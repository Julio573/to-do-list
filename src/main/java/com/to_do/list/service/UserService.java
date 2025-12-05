package com.to_do.list.service;

import com.to_do.list.dto.UpdatePasswordDTO;
import com.to_do.list.dto.UserRequestDTO;
import com.to_do.list.dto.UserResponseDTO;
import com.to_do.list.dto.mapper.UserMapper;
import com.to_do.list.entities.User;
import com.to_do.list.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = userMapper.toEntity(userRequestDTO);
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public UserResponseDTO updateEmail(Long id, String newEmail) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new RuntimeException("User not found"));

        user.setEmail(newEmail);
        user =  userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public UserResponseDTO updatePassword(Long id, UpdatePasswordDTO updatePasswordDTO) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(updatePasswordDTO.getOldPassword())) {
            throw new RuntimeException("Old password doesn't match");
        }

        user.setPassword(updatePasswordDTO.getNewPassword());
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper :: toDTO)
                .toList();
    }

}
