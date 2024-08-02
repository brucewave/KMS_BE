package com.group3.kindergartenmanagementsystem.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private Integer id;
    private String fullName;
    private String phoneNumber;
    private String password;
    private String email;
    private String address;
}
