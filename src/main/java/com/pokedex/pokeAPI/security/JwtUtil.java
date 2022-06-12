package com.pokedex.pokeAPI.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedex.pokeAPI.models.UserDetials;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class JwtUtil {
    public String decodeUsername(String token) throws JsonProcessingException {
        String[] chunks = token.split(" ")[1].split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        ObjectMapper mapper = new ObjectMapper();
        UserDetials user = mapper.readValue(payload, UserDetials.class);
        return user.getUsername();
    }

    public String generateChallenge() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] code = new byte[32];
        secureRandom.nextBytes(code);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(code);
    }
}
