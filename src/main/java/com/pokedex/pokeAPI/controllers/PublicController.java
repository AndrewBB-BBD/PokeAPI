package com.pokedex.pokeAPI.controllers;

import com.pokedex.pokeAPI.Utilities.S3BucketService;
import com.pokedex.pokeAPI.Utilities.URLBuilder;
import com.pokedex.pokeAPI.security.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/v1")
public class PublicController {
    @Value("${auth0.application_id}")
    String applicationId;

    @Value("${auth0.domain}")
    String domain;

    @Value("${auth0.callbackDomain}")
    private String callbackDomain;
    
    @Value("${app.port}")
    private String port;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    URLBuilder urlBuilder;

    @Autowired
    S3BucketService s3BucketService;


    @ApiOperation(value = "Login endpoint", notes = "Log in endpoint which will redirect users to login with valid " +
            "credentials and upon login be redirected to the API's official documentation.")
    @GetMapping("/login")
    public void login(HttpServletResponse httpResponse) throws IOException, NoSuchFieldException, IllegalAccessException {
        //use urlbuilder?
        String callbackURL = String.format("http://%1$s:%2$s/swagger-ui.html", callbackDomain, port);
        String loginURL = urlBuilder.baseUrl(domain, "authorize")
                        .clientId(applicationId).responseType("code")
                        .code_challenge_method("S256").redirect_uri(callbackURL)
                        .scope("openid").code_challenge(jwtUtil.generateChallenge())
                        .build();
        System.out.println(loginURL);
        httpResponse.sendRedirect(loginURL);
    }

    @ApiOperation(value = "Pokemon theme song", notes = "Fun end point which plays the original Pokemon theme song.")
    @GetMapping(value = "/sound")
    public ResponseEntity playPokemonThemeSong() throws FileNotFoundException {
        InputStream audioString = s3BucketService.test("sound_clips/Pokemon.mp3");
        InputStreamResource inputStreamResource = new InputStreamResource(audioString);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setCacheControl(CacheControl.noCache().getHeaderValue());
        httpHeaders.set("Content-Type", "audio/mp3");
        return new ResponseEntity(inputStreamResource, httpHeaders, HttpStatus.OK);
    }
}
