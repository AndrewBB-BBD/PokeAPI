package com.pokedex.pokeAPI.lib.Traversing;

public interface IterateBackward<T> extends Iterate<T> {
    public T previous();
    public boolean hasPrevious();

    default public boolean hasMove() { return hasPrevious();}
    default public T move() { return previous(); }

}
