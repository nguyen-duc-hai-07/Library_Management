package com.library.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {
    public String fullName;
    public String email;
    public String password;
    public String phoneNumber;
}
