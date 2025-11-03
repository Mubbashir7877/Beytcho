package com.beytcho.Beytcho.Services.interfA;

import com.beytcho.Beytcho.DTO.LoginRequest;
import com.beytcho.Beytcho.DTO.ResponseDTO;
import com.beytcho.Beytcho.DTO.UserDTO;
import com.beytcho.Beytcho.Entities.User;

public interface UserService {

    ResponseDTO registerUser(UserDTO registrationRequest);

    ResponseDTO loginUser(LoginRequest loginRequest);

    ResponseDTO getAllUsers();

    User getLoginUser();

    ResponseDTO getUserInfoAndOrderHistory();
}
