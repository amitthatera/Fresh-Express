package com.store.grocery.fresh_express.service;

import com.store.grocery.fresh_express.dto.UserDTO;
import com.store.grocery.fresh_express.model.User;

public interface UserService {

    User createUser(UserDTO userDTO);

    User updateUser(UserDTO userDTO, long userID);

    void deleteUser(long userID);

    User getUserByID(long userID);

}
