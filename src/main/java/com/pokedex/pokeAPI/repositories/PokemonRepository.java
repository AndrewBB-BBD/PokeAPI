package com.pokedex.pokeAPI.repositories;

import com.pokedex.pokeAPI.models.data.PokemonData;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepository extends CrudRepository<PokemonData, Integer> {
    List<PokemonData> findByidentifier(String identifier);

    
    List<PokemonData> findByid(int id);



}
