package com.store.grocery.fresh_express.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressDTO(
        @NotBlank(message = "House number is required!!") String houseNumber,
        @NotBlank(message = "Street is required!!") String street,
        @NotBlank(message = "Landmark is required!!") String landmark,
        @NotBlank(message = "City is required!!") String city,
        @NotBlank(message = "State is required!!") String state,
        @NotNull(message = "Postal code is required!!") @Min(value = 100000, message = "Postal code must be a valid 6-digit number!") @Max(value = 999999, message = "Postal code must be a valid 6-digit number!") Integer postalCode

) {
}
