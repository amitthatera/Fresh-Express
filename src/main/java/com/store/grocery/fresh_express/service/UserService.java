package com.store.grocery.fresh_express.service;

import com.store.grocery.fresh_express.dto.UserDTO;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO, long userID);

    void deleteUser(long userID);

    UserDTO getUserByID(long userID);

}
