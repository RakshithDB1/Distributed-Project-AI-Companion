package com.distributed_project_companion.account_service.service;


import com.distributed_project_companion.account_service.dto.auth.AuthResponse;
import com.distributed_project_companion.account_service.dto.auth.LoginRequest;
import com.distributed_project_companion.account_service.dto.auth.SignupRequest;

public interface AuthService {
    AuthResponse signup(SignupRequest request);

    AuthResponse login(LoginRequest request);
}
