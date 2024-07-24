package com.store.grocery.fresh_express.mapper;

import com.store.grocery.fresh_express.dto.UserDTO;
import com.store.grocery.fresh_express.model.User;
import com.store.grocery.fresh_express.shared.kernel.Mapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserMapper implements Mapper<User, UserDTO> {

    @Override
    public User mapToEntity(UserDTO dto) {
       return User.builder()
               .firstName(dto.firstName())
               .lastName(dto.lastName())
               .emailAddress(dto.emailAddress())
               .contactNumber(dto.contactNumber())
               .password(dto.password())
               .build();
    }

    @Override
    public UserDTO mapToDTO(User entity) {
        return new UserDTO(entity.getUserId(), entity.getFirstName(), entity.getLastName(), entity.getEmailAddress(),
                entity.getContactNumber(), entity.getPassword(), entity.isEnabled(), entity.isEmailVerified(),
                entity.isNumberVerified(), entity.getRoles(),Optional.empty(),Optional.empty());
    }


}
