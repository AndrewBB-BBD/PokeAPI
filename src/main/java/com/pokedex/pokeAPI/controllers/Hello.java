package com.pokedex.pokeAPI.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pokedex.pokeAPI.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/v1")
public class Hello {
    @Value("auth0.application_id")
    private String applicationId;

    @Value("auth0.domain")
    private String domain;

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("/login")
    public String firstPage(HttpServletResponse httpResponse) throws IOException {
        return "Hello World";
    }

    @PostMapping("/world")
    public String secondPage(@RequestHeader("authorization") String userToken) throws JsonProcessingException {
        String user = jwtUtil.decodeUsername(userToken);
        return "Welcome to secured endpoint: " + user;
    }

    @GetMapping(value = "/sound")
    public ResponseEntity playAudio(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:Pokemon.mp3");
        long length = file.length();

        InputStreamResource inputStreamResource = new InputStreamResource( new FileInputStream(file));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentLength(length);
        httpHeaders.setCacheControl(CacheControl.noCache().getHeaderValue());
        httpHeaders.set("Content-Type", "audio/mp3");
        return new ResponseEntity(inputStreamResource, httpHeaders, HttpStatus.OK);
    }
}
