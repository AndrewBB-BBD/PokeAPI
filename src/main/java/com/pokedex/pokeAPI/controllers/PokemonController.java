package com.pokedex.pokeAPI.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pokedex.pokeAPI.models.Pokemon;
import com.pokedex.pokeAPI.repositories.PokemonRepository;
import com.pokedex.pokeAPI.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class PokemonController {
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    PokemonRepository pokemonRepository;

    //@GetMapping("/pokemon")
    //public Pokemon createPokemon(@RequestBody Pokemon pokemon) {
    //   return pokemonRepository.save(pokemon);
    //}
}
