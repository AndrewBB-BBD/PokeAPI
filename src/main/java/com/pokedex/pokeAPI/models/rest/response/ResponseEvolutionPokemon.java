package com.pokedex.pokeAPI.models.rest.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@ApiModel(description = "Representing The Pokemon Evolution Chain Response ")
@Data
@RequiredArgsConstructor
public class ResponseEvolutionPokemon {
    @ApiModelProperty(notes = "The identifier of the pokemon", name = "Pokemon Identifier", position = 1, example = "bulbasaur")
    private final String pokemonIdentifier;
    
    @ApiModelProperty(notes = "If this pokemon the start of the chain", name = "Pokemon Chain Root", position = 2, example = "true")
    private final Boolean rootChain;

    @ApiModelProperty(notes = "The pokemon's postion in the chain. Starts with 0.\nNOTE: There can be multiple pokemon on the same 'rung' of the chain", name = "Pokemon Chain Position", position = 3, example = "1")
    private final Integer chainPosition;
}
