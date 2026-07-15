package com.sanjai.smartexpensetracker.controller;

import com.sanjai.smartexpensetracker.dto.ApiResponse;
import com.sanjai.smartexpensetracker.dto.LoginRequest;
import com.sanjai.smartexpensetracker.dto.LoginResponse;
import com.sanjai.smartexpensetracker.dto.RegisterRequest;
import com.sanjai.smartexpensetracker.dto.UserDto;
import com.sanjai.smartexpensetracker.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication APIs")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> register(
            @Valid @RequestBody RegisterRequest registerRequest) {
        UserDto userDto = authService.register(registerRequest);
        return new ResponseEntity<>(
                ApiResponse.success("User registered successfully", userDto),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Login user")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        return ResponseEntity.ok(
                ApiResponse.success("Login successful", loginResponse));
    }
}
