package com.pokedex.pokeAPI.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pokedex.pokeAPI.models.Pokemon;
import com.pokedex.pokeAPI.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

public class PokemonManagerController {

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("uploadPokemonCaught")
    public RequestEntity<Object> uploadPokemonCaught(@RequestHeader String authorization, @RequestBody Pokemon pokemon,
                                                     @RequestParam String pokemonName) throws JsonProcessingException {
        String user = jwtUtil.decodeUsername(authorization);
        // Check if user as space to keep pokemon -> fail if no space, otherwise
        // Give pokemon a name
        // upload pokemon return ok.
        return null;
    }

    @GetMapping("uploadPokemonCaught")
    public RequestEntity<Pokemon> getMyPokemon(@RequestHeader String authorization,
                                                     @RequestParam String pokemonName) throws JsonProcessingException {
        String user = jwtUtil.decodeUsername(authorization);
        // Check if user does own pokemon -> fail if not, otherwise
        // get Pokemon for user
        return null;
    }
}
