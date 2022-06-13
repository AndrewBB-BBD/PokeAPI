package com.pokedex.pokeAPI.models.data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Id;

import java.util.List;

import lombok.Data;

@Data
@Entity()
@Table(name = "pokemon_species")
public class PokemonSpeciesData {

    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String identifier;

    private Integer generation_id;

    private Integer evolves_from_species_id;

    private Integer evolution_chain_id;

    private Integer color_id;

    private Integer shape_id;
    
    private Integer habitat_id;
    
    private Integer gender_rate;

    private Integer capture_rate;

    private Integer base_happiness;

    private Boolean is_baby;

    private Integer hatch_counter;

    private Boolean has_gender_differences;

    private Integer growth_rate_id;

    private Boolean forms_switchable;

    private Boolean is_legendary;

    private Integer is_mythical;

    private Integer order;

    private Integer conquest_order;


    @OneToMany(mappedBy = "pokemon_species",
               orphanRemoval = true,
               fetch = FetchType.LAZY,
               cascade = CascadeType.ALL)
    private List<PokemonData> basePokemon;
}
