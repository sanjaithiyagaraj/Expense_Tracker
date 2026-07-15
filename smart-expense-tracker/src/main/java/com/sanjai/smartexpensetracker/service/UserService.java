package com.sanjai.smartexpensetracker.service;

import com.sanjai.smartexpensetracker.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto getUserProfile(String email);

    UserDto updateProfile(String email, UserDto userDto);

    List<UserDto> getAllUsers();
}
