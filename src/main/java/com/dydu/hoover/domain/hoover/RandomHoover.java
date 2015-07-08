package com.dydu.hoover.domain.hoover;

import com.dydu.hoover.domain.Move;
import com.dydu.hoover.domain.room.TileType;

import java.util.Optional;
import java.util.Random;

/**
 * Random implementation of {@link Hoover}
 * Next move is pure random, and is retried if the move lands in a wall
 */
public class RandomHoover extends AbstractHoover {

    private Random random = new Random();

    @Override
    public Optional<Move> move() {
        int randomIndex = Math.abs(random.nextInt() % (Move.values().length));
        Move move = Move.values()[randomIndex];
        return isWall(move) ? move() : Optional.of(move);
    }
}
