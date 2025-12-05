package com.to_do.list.controller;

import com.to_do.list.dto.UpdateEmailDTO;
import com.to_do.list.dto.UserRequestDTO;
import com.to_do.list.dto.UserResponseDTO;
import com.to_do.list.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/test")
    public String checkStatus() {
        return "Hello, World!";
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO  userResponseDTO = userService.createUser(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    @PatchMapping("/{id}/email")
    public ResponseEntity<UserResponseDTO> updateEmail(@PathVariable Long id, @Valid @RequestBody UpdateEmailDTO updateEmailDTO) {
        UserResponseDTO userResponseDTO = userService.updateEmail(id, updateEmailDTO.getEmail());
        return ResponseEntity.ok(userResponseDTO);
    }
}
