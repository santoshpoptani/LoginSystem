package com.example.loginwithjwt.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.List;

@Data
@AllArgsConstructor
public class jwtResponse {
    private String token;
    private String username;
    private List<String> roles;
    private String message;
}
