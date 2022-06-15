package com.pokedex.pokeAPI.lib.Traversing;

public interface Iterate<T> {
    public T current();
    public T move();
    public boolean hasMove();
    public T stop();
    public T start();
}
