package com.pokedex.pokeAPI.models.data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.pokedex.pokeAPI.lib.Traversing.BackwardContainer;
import com.pokedex.pokeAPI.lib.Traversing.ForwardContainer;
import com.pokedex.pokeAPI.lib.Traversing.IterateBackward;
import com.pokedex.pokeAPI.lib.Traversing.IterateForward;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;
@Data
@Slf4j
@Entity()
@Table(name = "pokemon_species")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class PokemonSpeciesData implements ForwardContainer<List<PokemonSpeciesData>>, BackwardContainer<PokemonSpeciesData>, Serializable {
    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String identifier;

    private Integer generation_id;

    @JsonBackReference //stop infinite recursion (DO NOT LOMBOK TOSTRING! PLEASE OVERIDE IF YOU NEED IT)
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="evolves_from_species_id")
    private PokemonSpeciesData parent_species;
    
    @OneToMany(mappedBy="parent_species")
    private List<PokemonSpeciesData> child_species;
    
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
    @JsonIgnore
    private List<PokemonData> basePokemon;

    public String toString() {
        return "PokemonSpeciesData{" +
                "identifier='" + identifier + '\'' +
                '}';
    }

    @JsonIgnore
    @Override
    public IterateForward<List<PokemonSpeciesData>> get_forwardIterator() {
        return new IterateForward<List<PokemonSpeciesData>>() {
            private PokemonSpeciesData first = PokemonSpeciesData.this;
            private List<PokemonSpeciesData> last = null;
            private List<PokemonSpeciesData> positions = Arrays.asList(PokemonSpeciesData.this);

            @Override
            public boolean hasNext() {
                boolean next = positions.stream().anyMatch(ps -> ps.getChild_species().stream().anyMatch(ps2 -> (ps2 != null) ) );
                return next;
            }

            @Override
            public List<PokemonSpeciesData> next() {
                if (!hasNext()) {
                    log.warn("Called previous when there was nothing to call");
                    return positions; //fail safe
                }
                // I DON"T KNOW WHY BUT DO NOT SHORT CUT THIS OPPERATION (1.5h)
                List<PokemonSpeciesData> childs = positions.stream().flatMap(ps -> ps.getChild_species().stream()).toList();
                positions = childs;
                return positions = childs;
            }

            @Override
            public List<PokemonSpeciesData> current() {
                return positions;
            }

            @Override
            public List<PokemonSpeciesData> start() {
                if (first == null) {
                    first = positions.stream().findAny().get().get_backwardIterator().stop();   
                }
                return Arrays.asList(first);
            }

            @Override
            public List<PokemonSpeciesData> stop() {
                if (last != null) {
                    return last;
                }
                List<PokemonSpeciesData> hold = positions;
                while (hasNext()) {
                    next(); //change state
                }
                last = current(); //should be the final children
                positions = hold;
                return last;
            }
        };
    }

    @JsonIgnore
    @Override
    public IterateBackward<PokemonSpeciesData> get_backwardIterator() {
        return new IterateBackward<PokemonSpeciesData>() {
            private PokemonSpeciesData first = null;
            private PokemonSpeciesData last = null;
            private PokemonSpeciesData position = PokemonSpeciesData.this;

            @Override
            public boolean hasPrevious() {
                return position.getParent_species() != null;
            }

            @Override
            public PokemonSpeciesData previous() {
                if (!hasPrevious()) {
                    log.warn("Called previous when there was nothing to call");
                    return position; //fail safe
                }
                position = position.getParent_species();
                return position;
            }

            @Override
            public PokemonSpeciesData current() {
                return position;
            }

            @Override
            public PokemonSpeciesData start() {
                // define last parent?
                log.warn("Called for the imposible");
                return null;
            }

            @Override
            public PokemonSpeciesData stop() {
                if (last != null) {
                    return last;
                }
                PokemonSpeciesData hold = current();
                while (hasPrevious()) {
                    previous(); //change state
                }
                last = current(); //should be the root parent
                position = hold;
                return last;
            }
        };
    }
}
