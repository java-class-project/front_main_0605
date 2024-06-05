package com.example.teamplay_p.dto.auth;


import java.util.UUID;


public class LoginResponse {
    private String userId;
    private UUID userUuid;
    private String token;

    public LoginResponse(String userId, UUID userUuid,String token){
        this.userId = userId;
        this.userUuid = userUuid;
        this.token = token;
    }

    // Getter methods
    public String getUserId(){
        return userId;
    }

    public UUID getUserUuid(){
        return userUuid;
    }

    public String getToken() {
        return token;
    }
}
