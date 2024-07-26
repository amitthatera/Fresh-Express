package com.store.grocery.fresh_express.service.impl;

import com.store.grocery.fresh_express.model.Roles;
import com.store.grocery.fresh_express.repository.RolesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleInitializationService {

    private final RolesRepository rolesRepository;

    public RoleInitializationService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    public void initializeRoles(){
        List<Roles.RoleName> roleNames =List.of(Roles.RoleName.values());
        roleNames.forEach(roleName -> {
            String description = getRoleDescription(roleName);
            if(rolesRepository.findByRoleName(roleName).isEmpty()){
             Roles role = Roles.builder()
                     .roleName(roleName)
                     .roleDescription(description)
                     .build();
             rolesRepository.save(role);
            }
        });

    }

    private String getRoleDescription(Roles.RoleName roleName) {
        return switch (roleName) {
            case ROLE_SUPER_ADMIN -> "Super Administrator with all privileges.";
            case ROLE_ADMIN -> "Administrator with administrative privileges.";
            case ROLE_CUSTOMER -> "Regular user with basic access.";
            case ROLE_MANAGER -> "Manager with managerial responsibilities.";
            case ROLE_VENDOR -> "Provide products or services to customers through the platform.";
            case ROLE_DELIVERY_PARTNER -> "Responsible for delivering goods or packages to customers.";
        };
    }
}
