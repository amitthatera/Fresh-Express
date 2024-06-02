package com.store.grocery.fresh_express.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record VendorDTO(
        @NotBlank(message = "Vendor name is required!!") String vendorName,
        @Email(message = "Please provide a valid email address!!") @NotBlank(message = "Vendor email is required!!") String vendorEmail,
        @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Please provide a valid mobile number!!") @NotBlank(message = "Vendor mobile is required!!") String vendorMobile,
        @NotNull(message = "Vendor address is required!!") AddressDTO vendorAddress,
        List<ProductDTO> productList

) {
}
