package com.to_do.list.dto.mapper;

import com.to_do.list.dto.UserRequestDTO;
import com.to_do.list.dto.UserResponseDTO;
import com.to_do.list.entities.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toDTO(User user);
    User toEntity(UserRequestDTO dto);

}
