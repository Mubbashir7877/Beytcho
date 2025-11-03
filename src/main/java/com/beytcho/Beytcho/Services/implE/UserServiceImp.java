package com.beytcho.Beytcho.Services.implE;

import com.beytcho.Beytcho.DTO.LoginRequest;
import com.beytcho.Beytcho.DTO.ResponseDTO;
import com.beytcho.Beytcho.DTO.UserDTO;
import com.beytcho.Beytcho.Entities.User;
import com.beytcho.Beytcho.Enums.UserRole;
import com.beytcho.Beytcho.Exceptions.InvalidCredentialException;
import com.beytcho.Beytcho.Exceptions.NotFoundException;
import com.beytcho.Beytcho.Mappers.EntityDTOMapper;
import com.beytcho.Beytcho.Repositories.UserRepo;
import com.beytcho.Beytcho.Security.JwtUtils;
import com.beytcho.Beytcho.Services.interfA.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    private final EntityDTOMapper entityDTOMapper;


    @Override
    public ResponseDTO registerUser(UserDTO registrationRequest) {

        UserRole role = UserRole.USER;

        if (registrationRequest.getRole()!=null&& registrationRequest.getRole().equalsIgnoreCase("admin")){
            role = UserRole.ADMIN;
        }

        User user = User.builder()
                .name(registrationRequest.getName())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .role(role).build();

        User savedUser = userRepo.save(user);

        UserDTO userDTO = entityDTOMapper.mapUserToDTOBasic(savedUser);

        return ResponseDTO.builder()
                .status(200)
                .message("User Successfully Added")
                .user(userDTO)
                .build();

    }

    @Override
    public ResponseDTO loginUser(LoginRequest loginRequest) {

        User user = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow(()->new NotFoundException("Email not found"));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new InvalidCredentialException("Password does not match");
        }
        String token = jwtUtils.generateToken(user);

        return ResponseDTO.builder()
                .status(200)
                .message("User successfully logged in")
                .expirationTime("6 Month")
                .role(user.getRole().name())
                .build();
    }

    @Override
    public ResponseDTO getAllUsers() {

        List<User> users = userRepo.findAll();
        List<UserDTO> userDTOList = users.stream()
                .map(entityDTOMapper::mapUserToDTOBasic)
                .toList();

        return ResponseDTO.builder()
                .status(200)
                .userList(userDTOList)
                .build();
    }

    @Override
    public User getLoginUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        log.info("User Email is: " + email);
        return userRepo.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User Not found"));
    }

    @Override
    public ResponseDTO getUserInfoAndOrderHistory() {

        User user = getLoginUser();
        UserDTO userDTO = entityDTOMapper.mapUserDTOAddressAndOrderHistory(user);

        return ResponseDTO.builder()
                .status(200)
                .user(userDTO)
                .build();
    }
}
