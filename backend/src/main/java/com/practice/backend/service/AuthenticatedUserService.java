package com.practice.backend.service;

import com.practice.backend.model.AuthenticatedUser;
import com.practice.backend.model.entity.User;
import com.practice.backend.security.exception.NoCurrentUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticatedUserService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        return extractUserDetailsAndReturn(userService.getOneByEmail(email));
    }

    public User getCurrentUser() {
        AuthenticatedUser authenticatedUser = this.getCurrentUserDetails();
        if (authenticatedUser == null) {
            throw new NoCurrentUserException("Cannot find current user");
        }
        return userService.getOneByEmail(authenticatedUser.getUsername());
    }


    private UserDetails extractUserDetailsAndReturn(User user) {
        return new AuthenticatedUser(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }

    private AuthenticatedUser getCurrentUserDetails() {
        return (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
