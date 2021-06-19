package com.demo.controller;

import com.demo.model.AuthenticationResponse;
import com.demo.model.TokenDecodeRequest;
import com.demo.model.TokenRefreshRequest;
import com.demo.service.TokenDecodeService;
import com.demo.service.TokenRefreshService;
import io.jsonwebtoken.Claims;
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
@RequestMapping("/tokens")
public class TokenController {
    private static final Function<Claims, ResponseEntity<Claims>> successDecode =
            response -> new ResponseEntity<>(response, HttpStatus.OK);
    private static final Supplier<ResponseEntity<Claims>> unauthorizedDecode =
            () -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    private static final Function<AuthenticationResponse, ResponseEntity<AuthenticationResponse>> successRefresh =
            response -> new ResponseEntity<>(response, HttpStatus.OK);
    private static final Supplier<ResponseEntity<AuthenticationResponse>> unauthorizedRefresh =
            () -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    @Autowired
    private TokenDecodeService tokenDecodeService;

    @Autowired
    private TokenRefreshService tokenRefreshService;

    @PostMapping("/decode")
    public ResponseEntity<?> decode(@RequestBody TokenDecodeRequest request) {
        return tokenDecodeService.decode(request.getToken()).map(successDecode).orElseGet(unauthorizedDecode);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody TokenRefreshRequest request) {
        return tokenRefreshService.refresh(request.getRefreshToken()).map(successRefresh).orElseGet(unauthorizedRefresh);
    }
}
