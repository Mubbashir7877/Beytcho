package com.beytcho.Beytcho.Security;

import com.beytcho.Beytcho.Entities.User;
import com.beytcho.Beytcho.Exceptions.NotFoundException;
import com.beytcho.Beytcho.Repositories.UserRepo;
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
