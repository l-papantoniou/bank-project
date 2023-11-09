package com.bank.bankproject.web.rest;

import com.bank.bankproject.auth.AuthenticationRequest;
import com.bank.bankproject.auth.AuthenticationResponse;
import com.bank.bankproject.auth.RegisterRequest;
import com.bank.bankproject.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthenticationResource {

    private final Logger log = LoggerFactory.getLogger(AuthenticationResource.class);

    private static final String ENTITY_NAME = "user";

    private final AuthenticationService authenticationService;


    /**
     * {@code POST  /register} : Create a new user.
     *
     * @param request the request to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new AuthenticationResponse, or with status {@code 400 (Bad Request)} if the customer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request)
            throws URISyntaxException {
        log.debug("REST request to save a new user : {}", request);
        return ResponseEntity.ok(authenticationService.register(request));
    }

    /**
     * {@code POST  /authenticate} : Authenticate a user.
     *
     * @param request the request to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new AuthenticationResponse, or with status {@code 400 (Bad Request)} if the customer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request)
            throws URISyntaxException {
        log.debug("REST request to authenticate a user : {}", request);
        return ResponseEntity.ok(authenticationService.authenticate(request));

    }


}
