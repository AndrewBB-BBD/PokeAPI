package com.pokedex.pokeAPI.metrics;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class PokemonServiceHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        return Health.up().withDetail("very nice", true).build();
    }
}
