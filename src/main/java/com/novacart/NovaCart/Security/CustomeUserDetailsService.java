package com.novacart.NovaCart.Security;

import com.novacart.NovaCart.Entities.User;
import com.novacart.NovaCart.Exceptions.NotFoundException;
import com.novacart.NovaCart.Repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class CustomeUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username)
                .orElseThrow(()-> new NotFoundException("User/email not found."));
        return AuthUser.builder().user(user).build();
    }
}
