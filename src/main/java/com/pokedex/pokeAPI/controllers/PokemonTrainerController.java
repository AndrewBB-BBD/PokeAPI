package com.pokedex.pokeAPI.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pokedex.pokeAPI.models.Pokemon;
import com.pokedex.pokeAPI.models.TamePokemon;
import com.pokedex.pokeAPI.repositories.TrainerRepository;
import com.pokedex.pokeAPI.security.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class PokemonTrainerController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    TrainerRepository trainerRepository;

    @ApiOperation(value = "Upload new pokemon you caught", notes = "Caught a new pokemon? Save it to your user profile!")
    @PutMapping("/uploadPokemonCaught")
    public ResponseEntity<Object> uploadPokemonCaught(@RequestHeader String authorization, @RequestBody Pokemon pokemon,
                                                      @RequestParam String nickname) throws JsonProcessingException {
        String user = jwtUtil.decodeUsername(authorization);
        TamePokemon tamePokemon = new TamePokemon(user, nickname, pokemon);
        try {
            trainerRepository.save(tamePokemon);
            return new ResponseEntity<>("Awesome, you caught: " + pokemon.getIdentifier(), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Upload unsuccessful", HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Get your pokemon/best-friend", notes = "Get all your pokemon or an individual " +
            "by calling them by name")
    @PostMapping("/getMyPokemon")
    public ResponseEntity<Object> getMyPokemon(@RequestHeader String authorization,
                                               @RequestParam Optional<String> pokemonName) throws JsonProcessingException {
        String user = jwtUtil.decodeUsername(authorization);
        return pokemonName.<ResponseEntity<Object>>map(s
                -> new ResponseEntity<>(trainerRepository.findByUserIdAndPokemonNickname(user, s), HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(trainerRepository.findAllByUserId(user), HttpStatus.OK));
    }
}
