package com.pokedex.pokeAPI.models;


import lombok.Data;

@Data
public class AuthDetails {
    private String access_token;
    private String id_token;
    private String scope;
    private int expires_in;
    private String token_type;

}
