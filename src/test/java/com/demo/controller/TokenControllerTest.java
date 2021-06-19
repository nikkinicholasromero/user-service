package com.demo.controller;

import com.demo.model.TokenDecodeRequest;
import com.demo.service.TokenDecodeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TokenControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private TokenController target;

    @Mock
    private TokenDecodeService tokenDecodeService;

    private ObjectMapper objectMapper;

    @Mock
    private Claims claims;

    private TokenDecodeRequest request;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders.standaloneSetup(target).build();

        request = new TokenDecodeRequest();
        request.setToken("some_token");
    }

    @Test
    public void decode_whenSuccess() throws Exception {
        when(tokenDecodeService.decode(anyString()))
                .thenReturn(Optional.of(claims));

        mockMvc.perform(post("/tokens/decode")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(claims)));

        verify(tokenDecodeService).decode(request.getToken());
    }

    @Test
    public void decode_whenFailed() throws Exception {
        when(tokenDecodeService.decode(anyString()))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/tokens/decode")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isUnauthorized());

        verify(tokenDecodeService).decode(request.getToken());
    }
}
