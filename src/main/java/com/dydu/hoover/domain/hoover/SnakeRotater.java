package com.dydu.hoover.domain.hoover;

import com.dydu.hoover.domain.Move;

class SnakeRotater implements Rotater {
    private int turnCount;
    private Rotater rotation = Rotater.clockwise;

    public void reset() {
        this.turnCount = 0;
    };

    @Override
    public Move rotate(Move move) {
        turnCount++;
        Move answer = rotation.rotate(move);
        if (turnCount == 2) {
            rotation = rotation == Rotater.clockwise ? Rotater.counterClockwise : Rotater.clockwise;
            turnCount = 0;
        }
        return answer;
    }
}
