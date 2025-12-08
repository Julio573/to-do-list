package com.to_do.list.service;

import com.to_do.list.dto.UpdatePasswordDTO;
import com.to_do.list.dto.UserRequestDTO;
import com.to_do.list.dto.UserResponseDTO;
import com.to_do.list.dto.mapper.UserMapper;
import com.to_do.list.entities.User;
import com.to_do.list.exception.EmailNotFoundException;
import com.to_do.list.exception.IncorrectPasswordMatchException;
import com.to_do.list.exception.InvalidEmailException;
import com.to_do.list.exception.UserNotFoundException;
import com.to_do.list.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = userMapper.toEntity(userRequestDTO);

        userRepository.findByEmail(userRequestDTO.getEmail()).ifPresent(u -> {
            throw new InvalidEmailException("Email already exists");
        });

        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Transactional
    public UserResponseDTO updateEmail(Long id, String newEmail) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException("User not found with id" + id));

        userRepository.findByEmail(newEmail).ifPresent(u -> {
            throw new InvalidEmailException("Email already exists");
        });

        user.setEmail(newEmail);
        user =  userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Transactional
    public UserResponseDTO updatePassword(Long id, UpdatePasswordDTO updatePasswordDTO) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!user.getPassword().equals(updatePasswordDTO.getOldPassword())) {
            throw new IncorrectPasswordMatchException("Old password doesn't match");
        }

        user.setPassword(updatePasswordDTO.getNewPassword());
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return userMapper.toDTO(user);
    }

    @Transactional
    public void deleteByEmail(String email) {
        User user =  userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException("Email Address not found"));

        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper :: toDTO)
                .toList();
    }

}
