package com.example.BlogApp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
public  class JwtData {
    private String token;

    public JwtData(String token) {
        this.token = token;
    }

    // getters and setters
}

