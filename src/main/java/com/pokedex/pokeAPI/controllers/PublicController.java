package com.pokedex.pokeAPI.controllers;

import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedex.pokeAPI.services.S3BucketService;
import com.pokedex.pokeAPI.Utilities.URLBuilder;
import com.pokedex.pokeAPI.models.AuthDetails;
import com.pokedex.pokeAPI.security.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/v1")
public class PublicController {
    @Value("${auth0.application_id}")
    String applicationId;

    @Value("${auth0.domain}")
    String auth0Domain;

    @Value("${app.port}")
    private String port;

    @Value("${app.redirect_uri}")
    String redirect_uri;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    URLBuilder urlBuilder;

    @Autowired
    S3BucketService s3BucketService;

    @Operation(summary = "Login endpoint", description = "Log in endpoint which will redirect users to login with valid " +
            "credentials and upon login be redirected to the API's official documentation.")
    @GetMapping("/login")
    public void login(HttpServletResponse httpResponse) throws IOException, NoSuchFieldException, IllegalAccessException {
        String challenge = jwtUtil.generateChallenge();
        String loginURL = urlBuilder.baseUrl(auth0Domain, "authorize")
                    .clientId(applicationId).responseType("code")
                    .code_challenge_method("S256").redirect_uri(redirect_uri)
                    .scope("openid").code_challenge(challenge)
                    .state(challenge).build();
        httpResponse.sendRedirect(loginURL);
    }
    
    @Operation(summary = "Get ID token", description = "Easily get you id_token for accessing all endpoints.")
    @GetMapping(value = "/getToken")
    public ResponseEntity<AuthDetails> getToken(@RequestParam String code, @RequestParam String state) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("code", code);
        map.add("code_verifier", state);
        map.add("client_id", applicationId);
        map.add("redirect_uri", redirect_uri);
        map.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.exchange("https://dev-omuu4lwn.us.auth0.com/oauth/token", HttpMethod.POST, request, String.class);
        ObjectMapper mapper = new ObjectMapper();
        AuthDetails authDetails = mapper.readValue(response.getBody(), AuthDetails.class);
        return new ResponseEntity<>(authDetails, HttpStatus.OK);
    }

    @Operation(summary = "Pokemon theme song", description = "Fun end point which plays the original Pokemon theme song.")
    @GetMapping(value = "/sound")
    public ResponseEntity<InputStreamResource> playPokemonThemeSong() throws FileNotFoundException {
        InputStream audioString = s3BucketService.getS3Resource("sound_clips/Pokemon.mp3");
        InputStreamResource inputStreamResource = new InputStreamResource(audioString);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setCacheControl(CacheControl.noCache().getHeaderValue());
        httpHeaders.set("Content-Type", "audio/mp3");
        return new ResponseEntity<>(inputStreamResource, httpHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/image")
    public ResponseEntity<byte[]> getImageAsResponseEntity(@RequestParam int imageID) throws IOException {
    HttpHeaders headers = new HttpHeaders();
    InputStream in = s3BucketService.getS3Resource("images/" + imageID + ".png");
    byte[] media = IOUtils.toByteArray(in);
    headers.setCacheControl(CacheControl.noCache().getHeaderValue());
    headers.set("Content-Type", "image/png");

    ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
    return responseEntity;
    }
}
