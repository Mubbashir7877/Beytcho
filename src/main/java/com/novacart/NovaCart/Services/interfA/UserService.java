package com.novacart.NovaCart.Services.interfA;

import com.novacart.NovaCart.DTO.LoginRequest;
import com.novacart.NovaCart.DTO.ResponseDTO;
import com.novacart.NovaCart.DTO.UserDTO;
import com.novacart.NovaCart.Entities.User;

public interface UserService {

    ResponseDTO registerUser(UserDTO registrationRequest);

    ResponseDTO loginUser(LoginRequest loginRequest);

    ResponseDTO getAllUsers();

    User getLoginUser();

    ResponseDTO getUserInfoAndOrderHistory();
}
