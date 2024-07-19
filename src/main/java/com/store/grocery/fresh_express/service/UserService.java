package com.store.grocery.fresh_express.service;

import com.store.grocery.fresh_express.dto.ChangePasswordRequest;
import com.store.grocery.fresh_express.dto.PageableResponse;
import com.store.grocery.fresh_express.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);

    void deleteUser(long userID);

    PageableResponse<UserDTO> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);

    UserDTO getUserByID(long userID);

    UserDTO getUserByEmailAddress(String emailAddress);

    List<UserDTO> getUserByKeyword(String keyword);

    void updatePassword(long userId, ChangePasswordRequest request);

}
