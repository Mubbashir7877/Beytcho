package com.beytcho.Beytcho.Controller;

import com.beytcho.Beytcho.DTO.LoginRequest;
import com.beytcho.Beytcho.DTO.ResponseDTO;
import com.beytcho.Beytcho.DTO.UserDTO;
import com.beytcho.Beytcho.Services.interfA.UserService;
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
