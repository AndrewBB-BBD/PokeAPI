package com.pokedex.pokeAPI.controllers;

import com.pokedex.pokeAPI.models.data.PokemonData;
import com.pokedex.pokeAPI.models.data.PokemonType;
import com.pokedex.pokeAPI.repositories.PokemonRepository;
import com.pokedex.pokeAPI.repositories.PokemonRepositoryByType;
import com.pokedex.pokeAPI.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1")
public class GetPokemon {
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    PokemonRepository pokemonrepository;

    @Autowired
    PokemonRepositoryByType typerepo;

    @GetMapping(value = "/pokemonById/")
    @Operation(
            summary = "View a pokemon",
            description = "View a pokemon",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved Evolution Line",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = PokemonData.class))
                                    )
                            }
                    ),
                    @ApiResponse(responseCode  = "401", description  = "You are not authorized to view this Pokemon"),
                    @ApiResponse(responseCode  = "403", description  = "Accessing this Pokemon you were trying to reach is forbidden"),
                    @ApiResponse(responseCode  = "404", description  = "This pokemon was not found")
            }
    )

    public ResponseEntity<?> GetPokemonBy(@RequestParam int id){
        System.out.println(id);
    	if(pokemonrepository.findByid(id).size()!= 0) {
	    	return ResponseEntity.ok(pokemonrepository.findByid(id));
    	} else {
                return ResponseEntity.notFound().build();
            }
    	}

    @GetMapping(value = "/pokemonByIdentifier/")
    @Operation(
            summary = "View a pokemon",
            description = "View a pokemon",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved Pokemon",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = PokemonData.class))
                                    )
                            }
                    ),
                    @ApiResponse(responseCode  = "401", description  = "You are not authorized to view this Pokemon"),
                    @ApiResponse(responseCode  = "403", description  = "Accessing this Pokemon you were trying to reach is forbidden"),
                    @ApiResponse(responseCode  = "404", description  = "This pokemon was not found")
            }
    )
    public ResponseEntity<?> GetPokemonBy(@RequestParam String identifier){
    	if(pokemonrepository.findByidentifier(identifier).size() != 0) {
	    	return ResponseEntity.ok(pokemonrepository.findByidentifier(identifier));
    	}
    	else {
    		return ResponseEntity.notFound().build();
    	}
    }
    
    @GetMapping(value = "/pokemonByType/")
    @Operation(
            summary = "View pokemon by type",
            description = "View pokemon by type",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved Pokemon",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = PokemonType.class))
                                    )
                            }
                    ),
                    @ApiResponse(responseCode  = "401", description  = "You are not authorized to view this Evolution Line"),
                    @ApiResponse(responseCode  = "403", description  = "Accessing this Evolution Line you were trying to reach is forbidden"),
                    @ApiResponse(responseCode  = "404", description  = "This pokemon was not found")
            }
    )
    public ResponseEntity<?> GetPokemonByType(@RequestParam String ptype){
    	if(typerepo.findByptype(ptype).size() != 0) {
	    	return ResponseEntity.ok((typerepo.findByptype(ptype)));
    	}
    	else {
    		return ResponseEntity.notFound().build();
    	}
    }
}
