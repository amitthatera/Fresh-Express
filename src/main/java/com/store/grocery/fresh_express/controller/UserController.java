package com.store.grocery.fresh_express.controller;

import com.store.grocery.fresh_express.dto.ChangePasswordRequest;
import com.store.grocery.fresh_express.dto.PageableResponse;
import com.store.grocery.fresh_express.dto.RequestResponse;
import com.store.grocery.fresh_express.dto.UserDTO;
import com.store.grocery.fresh_express.service.UserService;
import com.store.grocery.fresh_express.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<RequestResponse> deleteUserById(@PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RequestResponse.builder()
                        .timestamp(Instant.now().toString())
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("User Deleted Successfully!!")
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestResponse> getUserById(@PathVariable long id) {
        UserDTO user = userService.getUserByID(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RequestResponse.builder()
                        .timestamp(Instant.now().toString())
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(user)
                        .build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<RequestResponse> getUserByEmail(@PathVariable String email) {
        UserDTO user = userService.getUserByEmailAddress(email);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RequestResponse.builder()
                        .timestamp(Instant.now().toString())
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(user)
                        .build());
    }

    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<RequestResponse> getUserByKeyword(@PathVariable String keyword) {
        List<UserDTO> users = userService.getUserByKeyword(keyword);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RequestResponse.builder()
                        .timestamp(Instant.now().toString())
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(users)
                        .build());
    }

    @GetMapping()
    public ResponseEntity<RequestResponse> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.USER_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        PageableResponse<UserDTO> users = userService.getAllUsers(pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RequestResponse.builder()
                        .timestamp(Instant.now().toString())
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(users)
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RequestResponse> updatePassword(@PathVariable long id,
                                                          @RequestBody ChangePasswordRequest request){
        userService.updatePassword(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RequestResponse.builder()
                        .timestamp(Instant.now().toString())
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("Password Changed Successfully!!")
                        .build());
    }


}
