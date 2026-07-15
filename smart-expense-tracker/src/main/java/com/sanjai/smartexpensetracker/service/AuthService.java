package com.sanjai.smartexpensetracker.service;

import com.sanjai.smartexpensetracker.dto.LoginRequest;
import com.sanjai.smartexpensetracker.dto.LoginResponse;
import com.sanjai.smartexpensetracker.dto.RegisterRequest;
import com.sanjai.smartexpensetracker.dto.UserDto;

public interface AuthService {

    UserDto register(RegisterRequest registerRequest);

    LoginResponse login(LoginRequest loginRequest);
}
