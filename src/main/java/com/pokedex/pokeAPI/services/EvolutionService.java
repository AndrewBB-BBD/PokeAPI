package com.pokedex.pokeAPI.services;

import com.pokedex.pokeAPI.repositories.PokemonRepository;
import com.pokedex.pokeAPI.repositories.PokemonSpeciesRepository;
import com.pokedex.pokeAPI.models.rest.response.ResponseEvolutionPokemon;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;
import java.util.stream.IntStream;

import com.pokedex.pokeAPI.models.data.PokemonSpeciesData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.LimitedDataBufferList;
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

        var requested = pokemonRepo.findByidentifier(pokemonIdentifier);

        if (requested.size() == 0) return Collections.emptyList(); //doesn't exist

        var requestPokemonS = requested.get(0).getPokemon_species();

        var rootPokemonS = requestPokemonS.get_backwardIterator().stop();


        var forward = rootPokemonS.get_forwardIterator();
        List<List<PokemonSpeciesData>> chain = new ArrayList<>(); //DO NOT USE Arrays.toList() (1h)
        chain.add(forward.current());
        do {
            chain.add(forward.next());
        } while (forward.hasNext());

        return IntStream.range(0, chain.size())
                        .mapToObj(i -> chain.get(i).stream()
                                                .flatMap(g -> g.getBasePokemon().stream()
                                                                            .map(p -> new ResponseEvolutionPokemon(p.getIdentifier(), i))
                                                        )
                                )
                        .flatMap(s -> s)
                        .toList();
    }

    //kept here to show how complicated it was before

    /*
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
        */
}
