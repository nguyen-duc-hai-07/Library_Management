package com.library.service;

import com.library.dto.request.UserFilterRequest;
import com.library.dto.request.UserRequest;
import com.library.dto.response.BookResponse;
import com.library.dto.response.FineResponse;
import com.library.dto.response.UserResponse;
import java.util.List;


public interface UserService {
    List<UserResponse> viewUsersWithFilter(UserFilterRequest filter) throws Exception;

    UserResponse createUser(UserRequest request) throws Exception;

    UserResponse viewUserById(int id) throws Exception;

    UserResponse updateUser(int id, UserRequest request) throws Exception;

    void deleteUser(int id) throws Exception;

    void softDeleteUser(int id) throws Exception;

    List<BookResponse> viewAllBooksByUser(int userId) throws Exception;

    List<FineResponse> viewAllFinesByUser(int userId) throws Exception;
}
