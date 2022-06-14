package com.pokedex.pokeAPI.models.data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "pokemon_z")
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class PokemonData {

    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String identifier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "species_id")
    private PokemonSpeciesData pokemon_species;

    @Column(nullable = false)
    private Integer height;

    @Column(nullable = false)
    private Integer weight;

    @Column(nullable = false)
    private Integer base_experience;

    @Column(nullable = false, name="`order`" )
    private Integer order;

    @Column(nullable = false)
    private Boolean is_default;

    public PokemonData(String identifier, PokemonSpeciesData pokemon_species, Integer height, Integer weight, Integer base_experience, Integer order, Boolean is_default) {
        this.identifier = identifier;
        this.pokemon_species = pokemon_species;
        this.height = height;
        this.weight = weight;
        this.base_experience = base_experience;
        this.order = order;
        this.is_default = is_default;
    }
}
