package com.pokedex.pokeAPI.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tame_pokemon")
public class TamePokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int record_id;
    @Column(name = "user_id")
    private String userId;
    private Long pokemon_id;
    @Column(name = "pokemon_nickname")
    private String pokemonNickname;
    private String identifier;
    private int species_id;
    private int height;
    private int weight;
    private int base_experience;
    @Column(name = "`order`")
    private int order;
    private int is_default;

    public TamePokemon(String userId, String pokemonNickname, Pokemon pokemon) {
        this.userId = userId;
        this.pokemon_id = pokemon.getId();
        this.pokemonNickname = pokemonNickname;
        this.identifier = pokemon.getIdentifier();
        this.species_id = pokemon.getSpecies_id();
        this.height = pokemon.getHeight();
        this.weight = pokemon.getWeight();
        this.base_experience = pokemon.getBase_experience();
        this.order = pokemon.getOrder();
        this.is_default = pokemon.getIs_default();
    }

    public TamePokemon() {}
}
