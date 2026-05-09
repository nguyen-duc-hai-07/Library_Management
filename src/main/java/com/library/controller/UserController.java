package com.library.controller;


import com.library.dto.request.UserRequest;
import com.library.dto.response.BookResponse;
import com.library.dto.response.FineResponse;
import com.library.dto.response.UserResponse;
import com.library.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable int id) throws Exception {
        log.info("Get/api/v1/users/{}",id);
        return userService.viewUserById(id);
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest request) throws Exception {
        log.info("Post/api/v1/users");
        return userService.createUser(request);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable int id, @RequestBody UserRequest request) throws Exception {
        log.info("Put/api/v1/users/{}",id);
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) throws Exception {
        log.info("Delete/api/v1/users/{}",id);
        userService.deleteUser(id);
    }

    @PatchMapping("/{id}")
    public void softDeleteUser(@PathVariable int id) throws Exception {
        log.info("Patch/api/v1/users/{}",id);
        userService.softDeleteUser(id);
    }

    @GetMapping("/{id}/books")
    public List<BookResponse> viewAllBooksByUser(@PathVariable int id) throws Exception {
        log.info("Get/api/v1/users/{}/books",id);
        return userService.viewAllBooksByUser(id);
    }

    @GetMapping("/{id}/fines")
    public List<FineResponse> viewAllFinesByUser(@PathVariable int id) throws Exception {
        log.info("Get/api/v1/users/{}/fines",id);
        return userService.viewAllFinesByUser(id);
    }
}
