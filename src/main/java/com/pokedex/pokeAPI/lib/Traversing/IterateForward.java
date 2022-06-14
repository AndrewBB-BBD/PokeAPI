package com.pokedex.pokeAPI.lib.Traversing;

public interface IterateForward<T> extends Iterate<T> {
    public T next();
    public boolean hasNext();

    default public boolean hasMove() { return hasNext();}
    default public T move() { return next(); }

}
