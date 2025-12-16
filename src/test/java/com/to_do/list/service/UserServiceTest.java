package com.to_do.list.service;

import com.to_do.list.dto.UserRequestDTO;
import com.to_do.list.dto.UserResponseDTO;
import com.to_do.list.dto.mapper.UserMapper;
import com.to_do.list.entities.User;
import com.to_do.list.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;


    @Nested
    class SaveUserTests {

        @Test
        @DisplayName("Should create user successfully when everything is ok")
        void shouldCreateUserSuccessfullyWhenEmailDoesNotExist() {
            UserRequestDTO userRequestDTO = new UserRequestDTO();
            userRequestDTO.setName("julio");
            userRequestDTO.setEmail("julio@teste.com");
            userRequestDTO.setPassword("Test@157");

            User user = new User();
            user.setName(userRequestDTO.getName());
            user.setEmail(userRequestDTO.getEmail());
            user.setPassword(userRequestDTO.getPassword());

            when(userMapper.toEntity(userRequestDTO)).thenReturn(user);
            when(userRepository.findByEmail(userRequestDTO.getEmail())).thenReturn(Optional.empty());
            when(userRepository.save(user)).thenReturn(user);

            UserResponseDTO userResponseDTO = new UserResponseDTO();
            userResponseDTO.setName(userRequestDTO.getName());
            userResponseDTO.setEmail(userRequestDTO.getEmail());

            when(userMapper.toDTO(user)).thenReturn(userResponseDTO);

            var result = userService.createUser(userRequestDTO);

            assertThat(result).isNotNull();
            assertThat(result.getName()).isEqualTo(userRequestDTO.getName());
            assertThat(result.getEmail()).isEqualTo(userRequestDTO.getEmail());

            verify(userMapper).toEntity(userRequestDTO);
            verify(userRepository).findByEmail(userRequestDTO.getEmail());
            verify(userRepository).save(user);
            verify(userMapper).toDTO(user);


        }
    }



}

