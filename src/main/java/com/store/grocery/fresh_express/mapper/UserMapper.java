package com.store.grocery.fresh_express.mapper;

import com.store.grocery.fresh_express.dto.UserDTO;
import com.store.grocery.fresh_express.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDTO(User user);

    User toUser(UserDTO userDTO);
}
