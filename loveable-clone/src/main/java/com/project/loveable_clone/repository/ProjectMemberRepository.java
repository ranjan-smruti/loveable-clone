package com.project.loveable_clone.repository;

import com.project.loveable_clone.entity.Project;
import com.project.loveable_clone.entity.ProjectMember;
import com.project.loveable_clone.entity.ProjectMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {
    List<ProjectMember> findByIdProjectId(Long projectId);

    @Query("""
           SELECT pm.project
           FROM ProjectMember pm
           WHERE pm.id.projectId = :projectId
           AND pm.id.userId = :userId
           """)
    Optional<Project> findProjectByProjectIdAndUserId(
            @Param("projectId") Long projectId,
            @Param("userId") Long userId
    );
}
