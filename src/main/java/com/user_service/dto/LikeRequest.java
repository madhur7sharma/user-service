package com.user_service.dto;

import lombok.Data;

@Data
public class LikeRequest {

    private Long userId;

    private LikeStates action;
}
