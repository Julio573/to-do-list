package com.to_do.list.service;

import com.to_do.list.dto.UpdatePasswordDTO;
import com.to_do.list.dto.UserRequestDTO;
import com.to_do.list.dto.UserResponseDTO;
import com.to_do.list.dto.mapper.UserMapper;
import com.to_do.list.entities.User;
import com.to_do.list.exception.IncorrectPasswordMatchException;
import com.to_do.list.exception.InvalidEmailException;
import com.to_do.list.exception.UserNotFoundException;
import com.to_do.list.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserRequestDTO userRequestDTO;
    private UserResponseDTO userResponseDTO;
    private UpdatePasswordDTO updatePasswordDTO;

    private static final Long USER_ID = 1L;
    private static final String NAME = "julio";
    private static final String EMAIL = "julio@teste.com";
    private static final String PASSWORD = "Test@157";
    private static final String NEWPASSWORD = "NewTest@157";

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(USER_ID);
        user.setName(NAME);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);

        userRequestDTO = new UserRequestDTO(NAME, EMAIL, PASSWORD);
        userResponseDTO = new UserResponseDTO(NAME, EMAIL);
        updatePasswordDTO = new UpdatePasswordDTO(PASSWORD,  NEWPASSWORD);
    }

    @Nested
    class SaveUserTests {

        @Test
        @DisplayName("Should create user successfully when everything is ok")
        void shouldCreateUserSuccessfullyWhenEmailDoesNotExist() {

            when(userMapper.toEntity(userRequestDTO)).thenReturn(user);
            when(userRepository.findByEmail(userRequestDTO.getEmail())).thenReturn(Optional.of(user));
            when(userRepository.save(user)).thenReturn(user);

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

        @Test
        @DisplayName("Should throw InvalidEmailException when the email is already registered")
        void shouldThrowInvalidEmailExceptionWhenEmailIsAlreadyRegistered() {

            User userExists = new User();
            userExists.setEmail(userRequestDTO.getEmail());

            when(userRepository.findByEmail(userRequestDTO.getEmail())).thenReturn(Optional.of(userExists));

            InvalidEmailException exception = assertThrows(
                    InvalidEmailException.class,
                    () -> userService.createUser(userRequestDTO)
            );

            assertThat(exception.getMessage()).isEqualTo("Email already exists");

            verify(userRepository).findByEmail(userRequestDTO.getEmail());
            verify(userRepository, never()).save(any());
        }
    }

    @Nested
    class UpdateEmailTests {

        @Test
        @DisplayName("Should update email when the new email isn't registered")
        void shouldUpdateEmailWhenNewEmailIsNotRegistered() {

            String newEmail = "newEmail@Teste.com";

            UserResponseDTO userResponseDTO = new UserResponseDTO();
            userResponseDTO.setEmail(newEmail);

            when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
            when(userRepository.findByEmail(newEmail)).thenReturn(Optional.empty());
            when(userRepository.save(user)).thenReturn(user);
            when(userMapper.toDTO(user)).thenReturn(userResponseDTO);

            var result = userService.updateEmail(user.getId(), newEmail);

            assertThat(result).isNotNull();
            assertThat(result.getEmail()).isEqualTo(newEmail);

            verify(userRepository).findById(user.getId());
            verify(userRepository).findByEmail(newEmail);
            verify(userRepository).save(user);
        }

        @Test
        @DisplayName("Should throw UserNotFoundException when user's ID isn't found")
        void shouldThrowUserNotFoundExceptionWhenUserIsNotFound() {
            when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

            UserNotFoundException userNotFoundException = assertThrows(
                    UserNotFoundException.class,
                    () -> userService.updateEmail(user.getId(), userRequestDTO.getEmail())
            );

            assertThat(userNotFoundException.getMessage()).isEqualTo("User not found with id" + user.getId());

            verify(userRepository).findById(user.getId());
            verify(userRepository, never()).save(any());
            verify(userRepository, never()).findByEmail(any());
        }

        @Test
        @DisplayName("Should throw InvalidEmailException when the new email is already registered")
        void ShouldThrowInvalidEmailExceptionWhenNewEmailIsAlreadyRegistered() {

            String newEmail = "newEmail@Test.com";

            when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
            when(userRepository.findByEmail(newEmail)).thenReturn(Optional.of(user));

            InvalidEmailException invalidEmailException = assertThrows(
                    InvalidEmailException.class,
                    () -> userService.updateEmail(user.getId(), newEmail));

            assertThat(invalidEmailException.getMessage()).isEqualTo("Email already exists");
            verify(userRepository).findById(user.getId());
            verify(userRepository).findByEmail(newEmail);
            verify(userRepository, never()).save(any());
        }
    }

    @Nested
    class UpdatePasswordTests {

        @Test
        @DisplayName("Should update user's password successfully")
        void shouldUpdateUserPasswordSuccessfully() {

            when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
            when(userRepository.save(user)).thenReturn(user);
            when(userMapper.toDTO(user)).thenReturn(userResponseDTO);

            var result = userService.updatePassword(user.getId(), updatePasswordDTO);

            assertThat(result).isNotNull();
            assertThat(user.getPassword()).isEqualTo(updatePasswordDTO.getNewPassword());

            verify(userRepository).findById(user.getId());
            verify(userRepository).save(user);
            verify(userMapper).toDTO(user);
        }

        @Test
        @DisplayName("Should throw an error when user's id is not found")
        void shouldThrowAnErrorWhenUserIsNotFound() {
            when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

            UserNotFoundException userNotFoundException = assertThrows(
                    UserNotFoundException.class,
                    () -> userService.updatePassword(user.getId(), updatePasswordDTO)
            );

            assertThat(userNotFoundException.getMessage()).isEqualTo("User not found with id");

            verify(userRepository).findById(user.getId());
            verify(userRepository, never()).save(any());
            verify(userRepository, never()).findByEmail(any());
        }

        @Test
        @DisplayName("Should throw an error when old password doesn't match")
        void shouldThrowAnErrorWhenOldPasswordDoesNotMatch() {

            when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

            String originalPassword = user.getPassword();

            IncorrectPasswordMatchException incorrectPasswordMatchException = assertThrows(
                    IncorrectPasswordMatchException.class,
                    () -> userService.updatePassword(user.getId(), updatePasswordDTO)
            );

            assertThat(incorrectPasswordMatchException.getMessage()).isEqualTo("Old password doesn't match");
            assertThat(user.getPassword()).isEqualTo(originalPassword);
            assertThat(user.getPassword()).isNotEqualTo(NEWPASSWORD);

            verify(userRepository).findById(user.getId());
            verify(userRepository, never()).save(any());
        }
    }

    @Nested
    class FindByIdTests {

        @Test
        @DisplayName("Should find user by Id")
        void shouldFindUserById() {

            when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
            when(userMapper.toDTO(user)).thenReturn(userResponseDTO);

            var result = userService.findById(user.getId());

            assertThat(result).isNotNull();
            assertThat(user.getName()).isEqualTo(result.getName());
            assertThat(user.getEmail()).isEqualTo(result.getEmail());

            verify(userRepository).findById(user.getId());
            verify(userMapper).toDTO(user);
            verify(userRepository, never()).save(any());

        }

        @Test
        @DisplayName("Should throw UserNotFoundException when user's ID isn't found")
        void shouldThrowUserNotFoundExceptionWhenUserIsNotFound() {
            when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

            UserNotFoundException userNotFoundException = assertThrows(
                    UserNotFoundException.class,
                    () -> userService.updateEmail(user.getId(), userRequestDTO.getEmail())
            );

            assertThat(userNotFoundException.getMessage()).isEqualTo("User not found");

            verify(userRepository).findById(user.getId());
            verify(userRepository, never()).save(any());
            verify(userRepository, never()).findByEmail(any());
        }
    }
}





