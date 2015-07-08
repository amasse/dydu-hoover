package com.dydu.hoover.domain.hoover;

import com.dydu.hoover.domain.Move;

import java.util.Optional;

public class SnakeHoover extends AbstractHoover{

    private Move move = Move.RIGHT;
    private Safety safety = new Safety();
    private Rotater rotater = new SnakeRotater();

    @Override
    public Optional<Move> move() {
        if (safety.isLocked()) {
            System.out.println("Found cyclic ref");
            move = Rotater.counterClockwise.rotate(move);
            rotater.reset();
            safety.reinit();
        }
        for (int i = 0; i < 4; i++) {
            if (!isWall(move)) {
                safety.add(move);
                return Optional.of(move);
            }
            move = rotater.rotate(move);
        }
        return Optional.empty();
    }
}
