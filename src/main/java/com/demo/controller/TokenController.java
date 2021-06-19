package com.demo.controller;

import com.demo.model.TokenDecodeRequest;
import com.demo.service.TokenDecodeService;
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
    private static final Function<Claims, ResponseEntity<Claims>> success =
            response -> new ResponseEntity<>(response, HttpStatus.OK);
    private static final Supplier<ResponseEntity<Claims>> unauthorized =
            () -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    @Autowired
    private TokenDecodeService tokenDecodeService;

    @PostMapping("/decode")
    public ResponseEntity<?> decode(@RequestBody TokenDecodeRequest request) {
        return tokenDecodeService.decode(request.getToken()).map(success).orElseGet(unauthorized);
    }
}
