package com.novacart.NovaCart.Controller;

import com.novacart.NovaCart.DTO.LoginRequest;
import com.novacart.NovaCart.DTO.ResponseDTO;
import com.novacart.NovaCart.DTO.UserDTO;
import com.novacart.NovaCart.Services.interfA.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping ("/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody UserDTO registrationRequest){
        return ResponseEntity.ok(userService.registerUser(registrationRequest));
    }

    @PostMapping ("/login")
    public ResponseEntity<ResponseDTO> loginUser(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }

}
