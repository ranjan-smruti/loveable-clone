package com.project.loveable_clone.mappers;

import com.project.loveable_clone.dto.member.MemberResponse;
import com.project.loveable_clone.entity.ProjectMember;
import com.project.loveable_clone.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberResponseMapper {

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "role", constant = "OWNER")
    MemberResponse toProjectMemberResponseFromOwner(UserEntity owner);

    @Mapping(target="userId", source="user.id")
    @Mapping(target="email", source="user.email")
    @Mapping(target="name", source="user.name")
    MemberResponse toProjectMemberResponseFromMember(ProjectMember projectMember);
}
