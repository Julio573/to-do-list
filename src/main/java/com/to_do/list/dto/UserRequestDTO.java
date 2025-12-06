package com.to_do.list.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    public static final String PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$";
    public static final String PASSWORD_MESSAGE = "Password must have at least 8 characters, including a letter, a number, and a special character";

    @NotBlank(message = "Please, add your name!")
    @Size(max = 255)
    private String name;

    @NotBlank(message = "Please, enter a valid email address!")
    @Size(max = 255)
    @Email
    private String email;

    @NotBlank(message = "Please, enter a password!")
    @Size(min = 8, max = 255)
    @Pattern(
            regexp = PASSWORD_REGEX,
            message = PASSWORD_MESSAGE
    )
    private String password;
}
