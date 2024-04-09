package com.example.BlogApp.DTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public  class JwtResponse {
    private long userId;
    private String message;
    private boolean result;
    private JwtData data;

    public JwtResponse(long userId,String message, boolean result, String token) {
        this.message = message;
        this.result = result;
        this.data = new JwtData(token);
        this.userId = userId;
    }

    // getters and setters
}
