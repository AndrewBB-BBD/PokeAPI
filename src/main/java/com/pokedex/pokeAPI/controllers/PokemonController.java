package com.pokedex.pokeAPI.controllers;
import com.pokedex.pokeAPI.models.Pokemon;
import com.pokedex.pokeAPI.models.data.PokemonData;
import com.pokedex.pokeAPI.models.data.PokemonSpeciesData;
import com.pokedex.pokeAPI.models.rest.response.ResponseEvolutionPokemon;
import com.pokedex.pokeAPI.repositories.PokemonRepository;
import com.pokedex.pokeAPI.repositories.PokemonSpeciesRepository;
import com.pokedex.pokeAPI.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.pokedex.pokeAPI.exception.ResourceNotFoundException;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;

@Slf4j
@RestController
@RequestMapping("/v1/pokemon")
public class PokemonController {
    /*@Value("auth0.application_id")
    private String applicationId;

    @Value("auth0.domain")
    private String domain;*/

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    PokemonRepository pokemonRepository;

    @Autowired
    PokemonSpeciesRepository pokemonSpeciesRepository;

    @Operation(
            summary = "Create a new pokemon",
            description = "Create a new pokemon",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully create a new pokemon",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = PokemonData.class)
                                    )
                            }
                    ),
                    @ApiResponse(responseCode  = "401", description  = "You are not authorized to create pokemon"),
                    @ApiResponse(responseCode  = "403", description  = "Accessing this endpoint is forbidden"),
                    @ApiResponse(responseCode  = "404", description  = "This pokemon was not created")
            }
    )
    @PostMapping()
    public PokemonData createPokemon(@RequestBody PokemonUpdateModel pokemon) {
        PokemonSpeciesData speciesData = pokemonSpeciesRepository
                .findById(pokemon.pokemon_species).orElseThrow(() -> new ResourceNotFoundException("Pokemon", "id", pokemon.pokemon_species));

        PokemonData pokemonData = new PokemonData(
                pokemon.identifier,
                speciesData,
                pokemon.height,
                pokemon.weight,
                pokemon.base_experience,
                pokemon.order,
                pokemon.is_default==1
        );
        return pokemonRepository.save(pokemonData);
    }

    @Operation(
            summary = "Update an existing pokemon",
            description = "Update an existing pokemon",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully updated an existing pokemon",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = PokemonData.class)
                                    )
                            }
                    ),
                    @ApiResponse(responseCode  = "401", description  = "You are not authorized to update a pokemon"),
                    @ApiResponse(responseCode  = "403", description  = "Accessing this endpoint is forbidden"),
                    @ApiResponse(responseCode  = "404", description  = "This pokemon was not updated")
            }
    )
    @PatchMapping()
    public PokemonData updatePokemon(@RequestBody PokemonUpdateModel pokemonDetails) throws ResourceNotFoundException {
        PokemonData pokemon = pokemonRepository
                .findById(pokemonDetails.id)
                .orElseThrow(() -> new ResourceNotFoundException("Pokemon", "id", pokemonDetails.id));

        PokemonSpeciesData speciesData = null;
        if (pokemonDetails.pokemon_species != null) {
            speciesData = pokemonSpeciesRepository
                    .findById(pokemonDetails.pokemon_species)
                    .orElseThrow(() -> new ResourceNotFoundException("Pokemon", "id", pokemonDetails.pokemon_species));

        }

        pokemon.setIdentifier(pokemonDetails.identifier != null ? pokemonDetails.identifier : pokemon.getIdentifier());
        pokemon.setPokemon_species(speciesData != null ? speciesData : pokemon.getPokemon_species());
        pokemon.setHeight(pokemonDetails.height != null ? pokemonDetails.height : pokemon.getHeight());
        pokemon.setWeight(pokemonDetails.weight != null ? pokemonDetails.weight : pokemon.getWeight());
        pokemon.setBase_experience(pokemonDetails.base_experience != null ? pokemonDetails.base_experience : pokemon.getBase_experience());
        pokemon.setOrder(pokemonDetails.order != null ? pokemonDetails.order : pokemon.getOrder());
        pokemon.setIs_default(pokemonDetails.is_default != null ? pokemonDetails.is_default == 1 : pokemon.getIs_default());

        PokemonData data = pokemonRepository.save(pokemon);

        return data;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    private static class PokemonUpdateModel {

        private Integer id;
        private String identifier;
        private Integer pokemon_species;

        private Integer height;
        private Integer weight;
        private Integer base_experience;
        private Integer order;
        private Integer is_default;

    }




}