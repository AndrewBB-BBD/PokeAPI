package com.pokedex.pokeAPI.models.data;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity

@Table(name = "`pokemon_types_z`")
public class PokemonType {

    @Id
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private String identifier;

    @Column(nullable = false)
    private String ptype;


}
