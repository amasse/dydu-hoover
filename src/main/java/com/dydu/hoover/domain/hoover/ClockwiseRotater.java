package com.dydu.hoover.domain.hoover;

import com.dydu.hoover.domain.Move;

/**
 * Created by Norel on 30/06/2015.
 */
public class ClockwiseRotater implements Rotater {
    @Override
    public Move rotate(Move move) {
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
    }
}
