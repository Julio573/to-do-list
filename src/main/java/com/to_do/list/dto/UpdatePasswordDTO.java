package com.to_do.list.dto;

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
public class UpdatePasswordDTO {

    @NotBlank(message = "Please, enter a password!")
    @Size(min = 8, max = 255)
    @Pattern(
            regexp = UserRequestDTO.PASSWORD_REGEX,
            message = UserRequestDTO.PASSWORD_MESSAGE
    )
    private String oldPassword;

    @NotBlank(message = "Please, enter a password!")
    @Size(min = 8, max = 255)
    @Pattern(
            regexp = UserRequestDTO.PASSWORD_REGEX,
            message = UserRequestDTO.PASSWORD_MESSAGE
    )
    private String newPassword;
}
