package com.pokedex.pokeAPI.controllers;

import com.pokedex.pokeAPI.models.rest.response.ResponseEvolutionPokemon;
import com.pokedex.pokeAPI.services.EvolutionService;
import com.pokedex.pokeAPI.security.JwtUtil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
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
public class EvolutionController {
    //private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass()); // as per documentation https://www.slf4j.org/faq.html#declared_static

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    EvolutionService evolutionService;

    @RequestMapping(value = "/evolution/{pokemonIdentifier}", method = RequestMethod.GET)
    @ApiOperation(value = "View a pokemon evolution line")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved Evolution Line", responseContainer = "List", response = ResponseEvolutionPokemon.class),
            @ApiResponse(code = 401, message = "You are not authorized to view this Evolution Line"),
            @ApiResponse(code = 403, message = "Accessing this Evolution Line you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "This pokemon was not found")
        }
    )
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
