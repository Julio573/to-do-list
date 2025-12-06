package com.to_do.list.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorDTO {

    private int status;
    private String message;

    @JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
    private LocalDateTime timestamp;
}
