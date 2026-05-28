package com.library.service.impl;

import com.library.dto.request.UserFilterRequest;
import com.library.dto.request.UserRequest;
import com.library.dto.response.BookResponse;
import com.library.dto.response.FineResponse;
import com.library.dto.response.UserResponse;
import com.library.model.User;
import com.library.repository.UserRepository;
import com.library.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponse> filter(UserFilterRequest filter) {
        log.info("View users with filter: keyword = {}, page = {}, size = {} ",
                filter.getKeyword(),
                filter.getPage(),
                filter.getSize()
        );
        Pageable pageable = PageRequest.of(filter.getPage() - 1, filter.getSize());
        List<UserResponse> users = userRepository.findWithFilter(
                filter.getKeyword(),
                pageable
        );

        log.debug("Found {} users", users.size());

        return users;
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        log.info("create user");

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .passwordHash(request.getPasswordHash())
                .role(request.getRole())
                .status(request.getStatus())
                .build();
        User savedUser = userRepository.save(user);

        log.info("User created successfully with id={}", savedUser.getId());

        return getUserResponseOrThrow(savedUser.getId());
    }

    @Override
    public UserResponse viewUserById(int id) {
        log.info("View user with id={}", id);

        UserResponse user = getUserResponseOrThrow(id);

        log.info("User found successfully with id={}", id);

        return user;
    }

    @Override
    public UserResponse updateUser(int id, UserRequest request) {
        log.info("Update user with id={}", id);

        User user = getUserOrThrow(id);

        User updateUser = new User(
                user.getId(),
                request.getFullName(),
                request.getEmail(),
                request.getPhoneNumber(),
                request.getPasswordHash(),
                request.getRole(),
                request.getStatus()
        );

        User savedUser = userRepository.save(updateUser);

        log.info("User updated successfully with id={}", savedUser.getId());

        return getUserResponseOrThrow(savedUser.getId());
    }

    @Override
    public void softDeleteUser(int id) {
        log.info("Soft delete user with id={}", id);

        getUserOrThrow(id);

        userRepository.softDelete(id);

        log.info("User soft deleted successfully with id = {}", id);
    }

    @Override
    public List<BookResponse> viewAllBooksByUser(int userId) {
        log.info("View all books with userId = {}", userId);

        getUserResponseOrThrow(userId);

        List<BookResponse> books = userRepository.findBooksByUserId(userId);

        log.info("Found {} books", books.size());

        return books;
    }

    @Override
    public List<FineResponse> viewAllFinesByUser(int userId) {
        log.info("View all fines with userId = {}", userId);

        getUserResponseOrThrow(userId);

        List<FineResponse> fines = userRepository.findFinesByUserId(userId);

        log.info("Found {} fines", fines.size());

        return fines;
    }

    private User getUserOrThrow(int id) {
        return userRepository.findEntityById(id)
                .orElseThrow(() -> {
                    log.warn("User not found with id={}", id);
                    return new RuntimeException("User not found");
                });
    }

    private UserResponse getUserResponseOrThrow(int id) {
        return userRepository.findActiveById(id)
                .orElseThrow(() -> {
                    log.warn("User not found with id={}", id);
                    return new RuntimeException("User not found");
                });
    }
}
