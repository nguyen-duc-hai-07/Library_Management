package com.library.controller;


import com.library.dto.request.UserFilterRequest;
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

    @PostMapping("/filter")
    public List<UserResponse> filter(@RequestBody UserFilterRequest filter) throws Exception {
        log.info("View users");

        log.debug(
                "User filter reqeust: keyword={},status={},role={}, page={}, size={}",
                filter.getKeyword(),
                filter.getStatus(),
                filter.getRole(),
                filter.getPage(),
                filter.getSize()
        );

        return userService.filter(filter);
    }

    @GetMapping("/{id}")
    public UserResponse getId(@PathVariable int id) throws Exception {
        log.info("view user");

        log.debug("User id={}", id);

        return userService.viewUserById(id);
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest request) throws Exception {

        log.info("create user");

        return userService.createUser(request);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable int id, @RequestBody UserRequest request) throws Exception {
        log.info("Update user");

        log.debug("Update request: id={}", id);

        return userService.updateUser(id, request);
    }

    @PatchMapping("/{id}")
    public void softDelete(@PathVariable int id) throws Exception {
        log.info("soft delete user");

        log.debug("Soft delete id={}", id);

        userService.softDeleteUser(id);
    }

    @GetMapping("/{id}/books")
    public List<BookResponse> viewBooks(@PathVariable int id) throws Exception {
        log.info("View books by user");

        log.debug("User id={}", id);

        return userService.viewAllBooksByUser(id);
    }

    @GetMapping("/{id}/fines")
    public List<FineResponse> viewFines(@PathVariable int id) throws Exception {
        log.info("View fines by user");

        log.debug("User id={}", id);

        return userService.viewAllFinesByUser(id);
    }
}
