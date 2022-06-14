package com.pokedex.pokeAPI.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pokedex.pokeAPI.models.AuthDetails;
import com.pokedex.pokeAPI.models.Pokemon;
import com.pokedex.pokeAPI.models.TamePokemon;
import com.pokedex.pokeAPI.models.rest.response.ResponseEvolutionPokemon;
import com.pokedex.pokeAPI.repositories.TrainerRepository;
import com.pokedex.pokeAPI.security.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Upload your pokemon",
            description = "Caught a new pokemon? Upload load it to your user account.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Upload is successful",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = String.class))
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "You are not authorized to view this endpoint"),
                    @ApiResponse(responseCode = "403", description = "The endpoint you were trying to reach is forbidden"),
                    @ApiResponse(responseCode = "404", description = "Endpoint not found")
            }
    )
    @PutMapping("/uploadPokemonCaught")
    public ResponseEntity<String> uploadPokemonCaught(@RequestHeader String authorization, @RequestBody Pokemon pokemon,
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

    @Operation(
            summary = "Get your pokemon/best-friend",
            description = "Get all your pokemon or an individual by calling them by name",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful get all your pokemon",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = String.class))
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "You are not authorized to view this endpoint"),
                    @ApiResponse(responseCode = "403", description = "The endpoint you were trying to reach is forbidden"),
                    @ApiResponse(responseCode = "404", description = "Endpoint not found")
            }
    )
    @PostMapping("/getMyPokemon")
    public ResponseEntity<Object> getMyPokemon(@RequestHeader String authorization,
                                               @RequestParam Optional<String> pokemonName) throws JsonProcessingException {
        String user = jwtUtil.decodeUsername(authorization);
        return pokemonName.<ResponseEntity<Object>>map(s
                -> new ResponseEntity<>(trainerRepository.findByUserIdAndPokemonNickname(user, s), HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(trainerRepository.findAllByUserId(user), HttpStatus.OK));
    }
}
