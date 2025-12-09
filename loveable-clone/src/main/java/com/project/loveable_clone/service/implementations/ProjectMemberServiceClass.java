package com.project.loveable_clone.service.implementations;

import com.project.loveable_clone.ExceptionHandler.UnauthorizedException;
import com.project.loveable_clone.dto.member.InviteMemberRequest;
import com.project.loveable_clone.dto.member.MemberResponse;
import com.project.loveable_clone.dto.member.UpdateMemberRoleRequest;
import com.project.loveable_clone.entity.Project;
import com.project.loveable_clone.entity.ProjectMember;
import com.project.loveable_clone.entity.ProjectMemberId;
import com.project.loveable_clone.entity.UserEntity;
import com.project.loveable_clone.mappers.MemberResponseMapper;
import com.project.loveable_clone.repository.ProjectMemberRepository;
import com.project.loveable_clone.repository.ProjectRepository;
import com.project.loveable_clone.repository.UserRepository;
import com.project.loveable_clone.service.ProjectMemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectMemberServiceClass implements ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;
    private MemberResponseMapper memberResponseMapper;
    private final UserRepository userRepository;

    @Override
    public List<MemberResponse> getProjectMembers(Long projectId, Long userId) {
        Project project = getAccessibleProjectById(projectId, userId);

        List<MemberResponse> memberResponseList = new ArrayList<>();
        memberResponseList.add(memberResponseMapper.toProjectMemberResponseFromOwner(project.getOwner()));

        memberResponseList.addAll(
                projectMemberRepository.findByIdProjectId(projectId)
                        .stream()
                        .map(memberResponseMapper::toProjectMemberResponseFromMember)
                        .toList());

        return memberResponseList;
    }

    @Override
    public MemberResponse inviteMember(Long projectId, InviteMemberRequest request, Long userId) {
        Project project = getAccessibleProjectById(projectId, userId);

        if(!project.getOwner().getId().equals(userId)){
            throw new RuntimeException("Not allowed to invite member");
        }

        UserEntity invitee = userRepository.findByEmail(request.email()).orElseThrow();

        if(invitee.getId().equals(userId)){
            throw new RuntimeException("Cannot invite yourself");
        }

        ProjectMemberId  projectMemberId = new ProjectMemberId(projectId, invitee.getId());

        if(projectMemberRepository.existsById(projectMemberId))
        {
            throw new RuntimeException("Cannot invite once again");
        }

        ProjectMember member = ProjectMember.builder()
                .id(projectMemberId)
                .project(project)
                .user(invitee)
                .role(request.role())
                .invitedAt(Instant.now())
                .build();
        projectMemberRepository.save(member);

        return memberResponseMapper.toProjectMemberResponseFromMember(member);
    }

    @Override
    public MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request, Long userId) {
        Project project = getAccessibleProjectById(projectId, userId);

        if(!project.getOwner().getId().equals(userId)){
            throw new RuntimeException("Not owner, cannot update member role");
        }

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, memberId);
        ProjectMember member = projectMemberRepository.findById(projectMemberId).orElseThrow();

        member.setRole(request.role());
        projectMemberRepository.save(member);

        return memberResponseMapper.toProjectMemberResponseFromMember(member);
    }

    @Override
    public void removeProjectMember(Long projectId, Long memberId, Long userId) {
        Project project = getAccessibleProjectById(projectId, userId);

        if(!project.getOwner().getId().equals(userId)){
            throw new RuntimeException("Not owner, cannot remove member.");
        }

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, memberId);
        if(!projectMemberRepository.existsById(projectMemberId))
        {
            throw new RuntimeException("Member not found in project");
        }

        projectMemberRepository.deleteById(projectMemberId);
    }


    //INTERNAL FUNCTIONS
    private Project getAccessibleProjectById(Long projectId, Long userId){
        return projectRepository.findUserProjectsById(projectId,userId).orElseThrow(() -> new UnauthorizedException("Not accessible this user"));
    }
}
