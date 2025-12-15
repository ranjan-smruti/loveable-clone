package com.project.loveable_clone.mappers;

import com.project.loveable_clone.dto.auth.SignupRequest;
import com.project.loveable_clone.dto.auth.UserProfileResponse;
import com.project.loveable_clone.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toUserEntity(SignupRequest user);
    UserProfileResponse toUserProfileResponse(UserEntity user);
}
