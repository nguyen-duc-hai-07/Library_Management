package com.library.service.impl;

import com.library.dto.request.UserFilterRequest;
import com.library.dto.request.UserRequest;
import com.library.dto.response.BookResponse;
import com.library.dto.response.FineResponse;
import com.library.dto.response.UserResponse;
import com.library.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Override
    public List<UserResponse> filter(UserFilterRequest filter) throws Exception {
        return List.of();
    }

    @Override
    public UserResponse createUser(UserRequest request) throws Exception {
        return null;
    }

    @Override
    public UserResponse viewUserById(int id) throws Exception {
        return null;
    }

    @Override
    public UserResponse updateUser(int id, UserRequest request) throws Exception {
        return null;
    }

    @Override
    public void deleteUser(int id) throws Exception {

    }

    @Override
    public void softDeleteUser(int id) throws Exception {

    }

    @Override
    public List<BookResponse> viewAllBooksByUser(int userId) throws Exception {
        return List.of();
    }

    @Override
    public List<FineResponse> viewAllFinesByUser(int userId) throws Exception {
        return List.of();
    }
}
