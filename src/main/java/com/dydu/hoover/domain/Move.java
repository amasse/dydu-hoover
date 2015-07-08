package com.dydu.hoover.domain;

import java.util.function.Function;

/**
 * All possible moves (only x and y axis are allowed)
 */
public enum Move {
    LEFT(   p -> new Position(p.getX() - 1, p.getY())),
    RIGHT(  p -> new Position(p.getX() + 1, p.getY())),
    UP(     p -> new Position(p.getX(), p.getY() + 1)),
    DOWN(   p -> new Position(p.getX(), p.getY() -1));

    Move(Function<Position, Position> movement) {
        this.movement = movement;
    }

    /**
     * The function that computes the resulting position of the move, based on an initial position
     */
    private Function<Position, Position> movement;

    public Position from(Position position) {
        return movement.apply(position);
    }


}
