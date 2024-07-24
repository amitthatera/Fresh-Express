package com.store.grocery.fresh_express.service.impl;

import com.store.grocery.fresh_express.custom_exception.ApiException;
import com.store.grocery.fresh_express.custom_exception.ResourceNotFoundException;
import com.store.grocery.fresh_express.custom_exception.UserAlreadyExistsException;
import com.store.grocery.fresh_express.dto.ChangePasswordRequest;
import com.store.grocery.fresh_express.dto.PageableResponse;
import com.store.grocery.fresh_express.dto.UserDTO;
import com.store.grocery.fresh_express.mapper.UserMapper;
import com.store.grocery.fresh_express.model.Roles;
import com.store.grocery.fresh_express.model.User;
import com.store.grocery.fresh_express.repository.RolesRepository;
import com.store.grocery.fresh_express.repository.UserRepository;
import com.store.grocery.fresh_express.service.UserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


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
        User user = userMapper.mapToEntity(userDTO);
        boolean userExist = userRepository.findByEmailAddressIgnoreCase(user.getEmailAddress()).isPresent();
        if(userExist)
            throw new UserAlreadyExistsException("User already exists!!");
        user.setPassword(passwordEncoder.encode(userDTO.password()));
        Roles roles = rolesRepository.findByRoleName(Roles.RoleName.CUSTOMER)
                .orElseThrow(() -> new ResourceNotFoundException("Role Not Exist!!"));
        user.getRoles().add(roles);
        User newUser = userRepository.save(user);
        codeService.sendValidationEmail(newUser);
        return userMapper.mapToDTO(newUser);
    }


    @Override
    @CacheEvict(value = "user", key = "#userID")
    public void deleteUser(long userID) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Exist!!"));
        userRepository.delete(user);
    }

    @Override
    @Cacheable(value = "users", key = "#pageNumber + '-' + #pageSize + '-' + #sortBy + '-' + #sortDir")
    public PageableResponse<UserDTO> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir){
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> page = this.userRepository.findAll(pageable);
        return PageableResponse.getPageableResponse(page, userMapper);
    }

    @Override
    @Cacheable(value = "user", key = "#userID")
    public UserDTO getUserByID(long userID) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Exist!!"));
        return userMapper.mapToDTO(user);
    }

    @Override
    @Cacheable(value = "user", key = "#emailAddress")
    public UserDTO getUserByEmailAddress(String emailAddress) {
        User user = userRepository.findByEmailAddressIgnoreCase(emailAddress)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Exists!!"));
        return userMapper.mapToDTO(user);
    }

    @Override
    @Cacheable(value = "user", key = "#keyword")
    public List<UserDTO> getUserByKeyword(String keyword) {
        List<User> users = this.userRepository.findByFirstNameContainingIgnoreCase(keyword);
        return users.stream().map(userMapper::mapToDTO).toList();
    }

    @Override
    @CachePut(value = "user", key = "#userId")
    public void updatePassword(long userId, ChangePasswordRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Exists!!"));
        if(!passwordEncoder.matches(request.oldPassword(), user.getPassword())){
            throw new ApiException("Incorrect Password!!");
        } else if(passwordEncoder.matches(request.newPassword(), user.getPassword())){
            throw new ApiException("New Password Should Not Be The Same As The Current Password");
        } else if(!request.confirmPassword().equals(request.newPassword())){
            throw new ApiException("Password Does Not Match");
        }else{
            user.setPassword(passwordEncoder.encode(request.confirmPassword()));
            userRepository.save(user);
        }
    }

}
