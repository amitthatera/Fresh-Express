package com.store.grocery.fresh_express.dto;

import com.store.grocery.fresh_express.model.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Set;

public record UserDTO(
        @NotBlank(message = "Please Enter First Name!!") String firstName,
        @NotBlank(message = "Please Enter Last Name!!") String lastName,
        @Email(message = "Please Enter a Valid Email Address!") @NotBlank(message = "Email Address is Required!!") String emailAddress,
        @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Please Enter a Valid Contact Number!!") @NotBlank(message = "Contact Number is Required!!") String contactNumber,
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must be at least 8 characters long and contain a digit, a letter, and a special character (@#$%^&+=)!!")
        @NotBlank(message = "Password is Required!!") String password,
        Boolean isEnabled,
        Boolean isEmailVerified,
        Boolean isNumberVerified,
        Set<Roles> roles
) {
}
