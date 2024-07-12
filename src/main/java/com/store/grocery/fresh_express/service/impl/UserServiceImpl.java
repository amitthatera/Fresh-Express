package com.store.grocery.fresh_express.service.impl;

import com.store.grocery.fresh_express.custom_exception.ResourceNotFoundException;
import com.store.grocery.fresh_express.custom_exception.UserAlreadyExistsException;
import com.store.grocery.fresh_express.dto.UserDTO;
import com.store.grocery.fresh_express.mapper.UserMapper;
import com.store.grocery.fresh_express.model.Roles;
import com.store.grocery.fresh_express.model.User;
import com.store.grocery.fresh_express.repository.RolesRepository;
import com.store.grocery.fresh_express.repository.UserRepository;
import com.store.grocery.fresh_express.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final RolesRepository rolesRepository;

    private final ActivationCodeService codeService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,
                           PasswordEncoder passwordEncoder, RolesRepository rolesRepository,
                           ActivationCodeService codeService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;
        this.codeService = codeService;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toUser(userDTO);
        boolean userExist = userRepository.findByEmailAddressIgnoreCase(user.getEmailAddress()).isPresent();
        if(userExist)
            throw new UserAlreadyExistsException("User already exists!!");
        user.setPassword(passwordEncoder.encode(userDTO.password()));
        Roles roles = rolesRepository.findByRoleName(Roles.RoleName.USER)
                .orElseThrow(() -> new ResourceNotFoundException("Role Not Exist!!"));
        user.getRoles().add(roles);
        User newUser = userRepository.save(user);
        codeService.sendValidationEmail(newUser);
        return userMapper.toUserDTO(newUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, long userID) {
        return null;
    }

    @Override
    public void deleteUser(long userID) {

    }

    @Override
    public UserDTO getUserByID(long userID) {
        return null;
    }

    @Override
    public UserDTO findByEmailAddress(String emailAddress) {
        User user = userRepository.findByEmailAddressIgnoreCase(emailAddress)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Exists!!"));
        return userMapper.toUserDTO(user);
    }


}
