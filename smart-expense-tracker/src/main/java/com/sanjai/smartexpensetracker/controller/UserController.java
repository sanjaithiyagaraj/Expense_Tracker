package com.sanjai.smartexpensetracker.controller;

import com.sanjai.smartexpensetracker.dto.ApiResponse;
import com.sanjai.smartexpensetracker.dto.UserDto;
import com.sanjai.smartexpensetracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User Management APIs")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get current user profile")
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserDto>> getProfile(Authentication authentication) {
        UserDto userDto = userService.getUserProfile(authentication.getName());
        return ResponseEntity.ok(ApiResponse.success(userDto));
    }

    @Operation(summary = "Update current user profile")
    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<UserDto>> updateProfile(
            Authentication authentication,
            @Valid @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateProfile(authentication.getName(), userDto);
        return ResponseEntity.ok(
                ApiResponse.success("Profile updated successfully", updatedUser));
    }

    @Operation(summary = "Get all users (Admin only)")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success(users));
    }
}
