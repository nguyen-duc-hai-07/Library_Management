package com.library.service;

import com.library.dto.request.UserFilterRequest;
import com.library.dto.request.UserRequest;
import com.library.dto.response.BookResponse;
import com.library.dto.response.FineResponse;
import com.library.dto.response.UserResponse;

import java.util.List;


public interface UserService {
    List<UserResponse> filter(UserFilterRequest filter);

    UserResponse createUser(UserRequest request);

    UserResponse viewUserById(int id);

    UserResponse updateUser(int id, UserRequest request);

    void softDeleteUser(int id);

    List<BookResponse> viewAllBooksByUser(int userId);

    List<FineResponse> viewAllFinesByUser(int userId);
}
