package com.pokedex.pokeAPI.repositories;

import com.pokedex.pokeAPI.models.data.PokemonSpeciesData;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonSpeciesRepository extends CrudRepository<PokemonSpeciesData, Integer> {

}
