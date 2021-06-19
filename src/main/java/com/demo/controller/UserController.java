package com.demo.controller;

import com.demo.model.AuthenticationRequest;
import com.demo.model.AuthenticationResponse;
import com.demo.orchestrator.AuthenticationOrchestrator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
    private static final Function<AuthenticationResponse, ResponseEntity<AuthenticationResponse>> success =
            response -> new ResponseEntity<>(response, HttpStatus.OK);
    private static final Supplier<ResponseEntity<AuthenticationResponse>> unauthorized =
            () -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    @Autowired
    private AuthenticationOrchestrator orchestrator;

    @PostMapping("/auth")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return orchestrator.orchestrate(request).map(success).orElseGet(unauthorized);
    }
}
