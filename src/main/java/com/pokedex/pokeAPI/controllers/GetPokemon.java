package com.pokedex.pokeAPI.controllers;

import com.pokedex.pokeAPI.models.data.PokemonData;
import com.pokedex.pokeAPI.models.data.PokemonType;
import com.pokedex.pokeAPI.models.rest.response.ResponseEvolutionPokemon;
import com.pokedex.pokeAPI.repositories.PokemonRepository;
import com.pokedex.pokeAPI.repositories.PokemonRepositoryByType;
import com.pokedex.pokeAPI.services.EvolutionService;
import com.pokedex.pokeAPI.security.JwtUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import java.lang.invoke.MethodHandles;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/v1")
public class GetPokemon {
    //private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass()); // as per documentation https://www.slf4j.org/faq.html#declared_static

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    PokemonRepository pokemonrepository;

    @Autowired
    PokemonRepositoryByType typerepo;

    @GetMapping(value = "/pokemonById/")
    @ApiOperation(value = "View a pokemon")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved Pokemon", responseContainer = "List", response = PokemonData.class),
            @ApiResponse(code = 401, message = "You are not authorized to view this Pokemon"),
            @ApiResponse(code = 403, message = "Accessing the Pokemon you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "This pokemon was not found")
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
    @ApiOperation(value = "View a pokemon")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved Pokemon", responseContainer = "List", response = PokemonData.class),
            @ApiResponse(code = 401, message = "You are not authorized to view this Pokemon"),
            @ApiResponse(code = 403, message = "Accessing the Pokemon you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "This pokemon was not found")
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
    @ApiOperation(value = "View a pokemon")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved Pokemon by type", responseContainer = "List", response = PokemonType.class),
            @ApiResponse(code = 401, message = "You are not authorized to view this Pokemon"),
            @ApiResponse(code = 403, message = "Accessing the Pokemon you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "This pokemon was not found")
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
