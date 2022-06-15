package com.pokedex.pokeAPI.repositories;

import java.util.List;
import com.pokedex.pokeAPI.models.data.PokemonType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepositoryByType extends CrudRepository<PokemonType, Integer> {
    List<PokemonType> findByptype(String ptype);
}