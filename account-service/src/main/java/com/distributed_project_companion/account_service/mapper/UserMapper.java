package com.distributed_project_companion.account_service.mapper;


import com.distributed_project_companion.account_service.dto.auth.SignupRequest;
import com.distributed_project_companion.account_service.dto.auth.UserProfileResponse;
import com.distributed_project_companion.account_service.entity.User;
import com.distributed_project_companion.common_lib.dto.UserDto;
import com.distributed_project_companion.common_lib.security.JwtUserPrincipal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(SignupRequest signupRequest);

    @Mapping(source = "userId", target = "id")
    UserProfileResponse toUserProfileResponse(JwtUserPrincipal user);

    UserDto toUserDto(User user);

}
