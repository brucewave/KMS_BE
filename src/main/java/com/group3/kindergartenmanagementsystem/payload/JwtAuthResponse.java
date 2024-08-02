package com.group3.kindergartenmanagementsystem.payload;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Data
@Builder
public class JwtAuthResponse {
    private String accessToken;
    private final String tokenType = "Bearer";
    private Integer userId;
}
