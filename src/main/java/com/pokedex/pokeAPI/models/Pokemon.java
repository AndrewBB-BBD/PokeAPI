package com.pokedex.pokeAPI.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "pokemon_z")
@Entity

public class Pokemon {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String identifier;

    private Integer species_id;

    private Integer height;

    private Integer weight;

    private Integer base_experience;

    @Column(name="`order`")
    private Integer order;

    private Integer is_default;



}
