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

    /*public Pokemon() {

    }

    public Pokemon(Long id, String identifier, int species_id, int height, int weight, int base_experience, int order, int is_default) {
        this.id = id;
        this.identifier = identifier;
        this.species_id = species_id;
        this.height = height;
        this.weight = weight;
        this.base_experience = base_experience;
        this.order = order;
        this.is_default = is_default;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public int getSpeciesID() {
        return species_id;
    }

    public void setSpeciesID(int species_id) {
        this.species_id = species_id;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getBaseExperience() {return base_experience;}

    public void setBaseExperience(int base_experience) {
        this.base_experience = base_experience;
    }

    public int getOrder() {return order;}

    public void setOrder(int order) {
        this.order = order;
    }

    public int getIsDefault() {return is_default;}

    public void setIsDefault(int is_default) {
        this.is_default = is_default;
    }*/

}
