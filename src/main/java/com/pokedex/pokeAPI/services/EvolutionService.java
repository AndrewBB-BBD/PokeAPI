package com.pokedex.pokeAPI.services;

import com.pokedex.pokeAPI.models.rest.response.ResponseEvolutionPokemon;
import com.pokedex.pokeAPI.repositories.PokemonRepository;
import com.pokedex.pokeAPI.repositories.PokemonSpeciesRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.pokedex.pokeAPI.models.data.PokemonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service("EvoSrv")
@Slf4j
public class EvolutionService {

    @Autowired
    PokemonRepository pokemonRepo;
    
    @Autowired
    PokemonSpeciesRepository pokemonSpeciesRepo;

    public List<ResponseEvolutionPokemon> listEvolutions(String pokemonIdentifier) {
        log.info("Request for {}", pokemonIdentifier);
        var request = pokemonRepo.findByidentifier(pokemonIdentifier);

        if (request.size() == 0) return Collections.emptyList();

        var requestPokemon = request.get(0);

        var requestPokemonS = requestPokemon.getPokemon_species();
        
        var requestPokemonFamily = pokemonSpeciesRepo.getFamily(requestPokemonS.getEvolution_chain_id());

        requestPokemonFamily.sort((sp1, sp2) -> sp1.getOrder().compareTo(sp2.getOrder()));             

        List<ResponseEvolutionPokemon> ret = new ArrayList<ResponseEvolutionPokemon>();
        for (int i = 0; i < requestPokemonFamily.size(); i++) {
            var pokeSpecies = requestPokemonFamily.get(i);
            var pokes = pokeSpecies.getBasePokemon();
            for (PokemonData p : pokes) {
                ret.add(new ResponseEvolutionPokemon(p.getIdentifier(), i == 0, i));
            } 
        }

        log.info("Response from {} request was: {}", pokemonIdentifier, ret.toString());
        return ret;
     }
}
