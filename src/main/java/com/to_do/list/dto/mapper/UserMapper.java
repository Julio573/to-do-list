package com.to_do.list.dto.mapper;

import com.to_do.list.dto.UserRequestDTO;
import com.to_do.list.entities.User;
import jakarta.validation.constraints.Max;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserRequestDTO toDTO(User user);
    User toEntity(UserRequestDTO dto);

}
