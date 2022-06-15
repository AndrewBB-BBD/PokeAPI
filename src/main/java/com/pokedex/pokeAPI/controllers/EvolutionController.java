package com.pokedex.pokeAPI.controllers;

import com.pokedex.pokeAPI.models.rest.response.ResponseEvolutionPokemon;
import com.pokedex.pokeAPI.services.EvolutionService;
import com.pokedex.pokeAPI.security.JwtUtil;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1")
public class EvolutionController {
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    EvolutionService evolutionService;

    @Operation(
            summary = "View a pokemon evolution line",
            description = "View a pokemon evolution line",
            responses = {
                    @ApiResponse(
                        responseCode = "200",
                        description = "Successfully retrieved Evolution Line",
                        content = {
                                @Content(
                                        mediaType = "application/json",
                                        array = @ArraySchema(schema = @Schema(implementation = ResponseEvolutionPokemon.class))
                                )
                        }
                    ),
                    @ApiResponse(responseCode  = "401", description  = "You are not authorized to view this Evolution Line"),
                    @ApiResponse(responseCode  = "403", description  = "Accessing this Evolution Line you were trying to reach is forbidden"),
                    @ApiResponse(responseCode  = "404", description  = "This pokemon was not found")
            }
    )
    @RequestMapping(value = "/evolution/{pokemonIdentifier}", method = RequestMethod.GET)
    public ResponseEntity<?> getEvolution(@PathVariable String pokemonIdentifier, Model model) {
        log.info("Evo Request for {}", pokemonIdentifier);
        List<ResponseEvolutionPokemon> evoList = evolutionService.listEvolutions(pokemonIdentifier);
        
        if (evoList.isEmpty()) {
            log.info("Evo Request failed for {}", pokemonIdentifier);
            return ResponseEntity.notFound().build();
        }
        
        log.info("Returned {} responses for {}", evoList.size(), pokemonIdentifier);
        return ResponseEntity.ok(evoList);
    }
}
