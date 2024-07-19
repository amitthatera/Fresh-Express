package com.store.grocery.fresh_express.dto;

public record ChangePasswordRequest(
        String oldPassword,
        String newPassword,
        String confirmPassword
) {
}
