package com.sanjai.smartexpensetracker.service.impl;

import com.sanjai.smartexpensetracker.dto.UserDto;
import com.sanjai.smartexpensetracker.entity.User;
import com.sanjai.smartexpensetracker.exception.ResourceNotFoundException;
import com.sanjai.smartexpensetracker.repository.UserRepository;
import com.sanjai.smartexpensetracker.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto getUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setRole(user.getRole().name());
        return userDto;
    }

    @Override
    public UserDto updateProfile(String email, UserDto userDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        user.setFullName(userDto.getFullName());

        User updatedUser = userRepository.save(user);
        UserDto response = modelMapper.map(updatedUser, UserDto.class);
        response.setRole(updatedUser.getRole().name());
        return response;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> {
                    UserDto dto = modelMapper.map(user, UserDto.class);
                    dto.setRole(user.getRole().name());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
