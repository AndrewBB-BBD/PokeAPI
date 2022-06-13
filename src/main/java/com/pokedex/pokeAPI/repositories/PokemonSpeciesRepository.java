package com.pokedex.pokeAPI.repositories;

import com.pokedex.pokeAPI.models.data.PokemonSpeciesData;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface PokemonSpeciesRepository extends CrudRepository<PokemonSpeciesData, Integer> {
    @Query("SELECT psd FROM PokemonSpeciesData psd WHERE psd.evolution_chain_id = :fam")
    List<PokemonSpeciesData> getFamily(@Param("fam") Integer famId);
}
