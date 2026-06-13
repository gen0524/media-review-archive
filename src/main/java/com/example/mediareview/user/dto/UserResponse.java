package com.example.mediareview.user.dto;

import com.example.mediareview.user.entity.User;
import lombok.Getter;

@Getter
public class UserResponse {

    private Long id;
    private String username;

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }
}