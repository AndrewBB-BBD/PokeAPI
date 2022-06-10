package com.pokedex.pokeAPI.models;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Table(name = "pokemon2")
@Entity

public class Pokemon {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String identifier;

    private int species_id;

    private int height;

    private int weight;

    private int base_experience;

    @Column(name="`order`")
    private int order;

    private int is_default;



}
