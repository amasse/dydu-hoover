package com.dydu.hoover.domain;

@FunctionalInterface
public interface Rotater {

    Move rotate(Move move);

    default void reset(){}

    Rotater clockwise = move ->
    {
        switch (move) {
            case LEFT:
                return Move.UP;
            case UP:
                return Move.RIGHT;
            case RIGHT:
                return Move.DOWN;
            case DOWN:
                return Move.LEFT;
            default:
                return null;
        }
    };

    Rotater counterClockwise = move -> {
        switch (move) {
            case LEFT:
                return Move.DOWN;
            case DOWN:
                return Move.RIGHT;
            case RIGHT:
                return Move.UP;
            case UP:
                return Move.LEFT;
            default:
                return null;
        }
    };

    Rotater turnaround = move -> {
        switch (move) {
            case LEFT:
                return Move.RIGHT;
            case RIGHT:
                return Move.LEFT;
            case UP:
                return Move.DOWN;
            case DOWN:
                return Move.UP;
            default:
                return null;
        }
    };
}

