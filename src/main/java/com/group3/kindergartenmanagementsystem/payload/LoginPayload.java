package com.group3.kindergartenmanagementsystem.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginPayload {
    private String accessToken;
    private Integer userId;
}
