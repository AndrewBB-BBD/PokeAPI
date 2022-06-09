package com.pokedex.pokeAPI.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDetials {
    private String issuer;
    private String username;
    private String audience;
    private long issuedAt;
    private long expirationTime;

    public String getIssuer() {
        return issuer;
    }

    @JsonProperty("iss")
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getUsername() {
        return username;
    }

    @JsonProperty("sub")
    public void setUsername(String username) {
        this.username = username;
    }

    public String getAudience() {
        return audience;
    }

    @JsonProperty("aud")
    public void setAudience(String audience) {
        this.audience = audience;
    }

    public long getIssuedAt() {
        return issuedAt;
    }

    @JsonProperty("iat")
    public void setIssuedAt(long issuedAt) {
        this.issuedAt = issuedAt;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    @JsonProperty("exp")
    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }
}
