package com.pokedex.pokeAPI.repositories;

import com.pokedex.pokeAPI.models.TamePokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TrainerRepository extends JpaRepository<TamePokemon, String> {
    List<TamePokemon> findAllByUserId(String userId);
    TamePokemon findByUserIdAndPokemonNickname(String userId, String pokemonNickname);
}
