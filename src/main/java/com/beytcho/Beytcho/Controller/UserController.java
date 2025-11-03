package com.beytcho.Beytcho.Controller;

import com.beytcho.Beytcho.DTO.ResponseDTO;
import com.beytcho.Beytcho.Services.interfA.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get-all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/my-info")
    public ResponseEntity<ResponseDTO> getUserInfoAndHistory(){
        return ResponseEntity.ok(userService.getUserInfoAndOrderHistory());
    }


}
