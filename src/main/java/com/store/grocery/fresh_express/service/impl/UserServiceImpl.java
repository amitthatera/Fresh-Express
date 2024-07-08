package com.store.grocery.fresh_express.service.impl;

import com.store.grocery.fresh_express.dto.UserDTO;
import com.store.grocery.fresh_express.mapper.UserMapper;
import com.store.grocery.fresh_express.model.User;
import com.store.grocery.fresh_express.repository.UserRepository;
import com.store.grocery.fresh_express.service.UserService;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User createUser(UserDTO userDTO) {
        User user = userMapper.toUser(userDTO);
        boolean userExist = userRepository.findByEmailAddressIgnoreCase(user.getEmailAddress()).isPresent();
        if(userExist)
            throw new IllegalArgumentException("User already exists!!");
    }

    @Override
    public User updateUser(UserDTO userDTO, long userID) {
        return null;
    }

    @Override
    public void deleteUser(long userID) {

    }

    @Override
    public User getUserByID(long userID) {
        return null;
    }
}
