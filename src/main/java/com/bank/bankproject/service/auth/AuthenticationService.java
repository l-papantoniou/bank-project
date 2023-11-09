package com.bank.bankproject.service.auth;

import com.bank.bankproject.auth.AuthenticationRequest;
import com.bank.bankproject.auth.AuthenticationResponse;
import com.bank.bankproject.auth.RegisterRequest;
import com.bank.bankproject.domain.Role;
import com.bank.bankproject.domain.User;
import com.bank.bankproject.enums.Roles;
import com.bank.bankproject.security.config.JwtService;
import com.bank.bankproject.service.RoleService;
import com.bank.bankproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final RoleService roleService;

    private final AuthenticationManager authenticationManager;

    // Registers a new user based on the provided registration request
    public AuthenticationResponse register(RegisterRequest request) {
        // Create a new user entity from the registration request
        User user = createUser(request);

        // Save the user entity in the database
        userService.save(user);

        // Generate a JWT token for the registered user
        String jwtToken = jwtService.generateToken(user);

        // Return the JWT token in the authentication response
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Authenticate the user using the provided authentication manager
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Retrieve the user entity from the database based on the provided email
        User user = userService.findByEmail(request.getEmail())
                .orElseThrow();

        // Generate a JWT token for the authenticated user
        String jwtToken = jwtService.generateToken(user);

        // Return the JWT token in the authentication response
        return new AuthenticationResponse(jwtToken);
    }

    // Creates a new User entity from the provided registration request
    private User createUser(RegisterRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstname());
        user.setLastName(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Assign default role to the new user
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByDescription(Roles.ROLE_USER.getDescription()));
        user.setRoles(roles);


        return user;
    }

}
