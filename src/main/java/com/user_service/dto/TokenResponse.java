package com.user_service.dto;

import lombok.Data;

@Data
public class TokenResponse {
    private String token = "";
    private String message;
}
