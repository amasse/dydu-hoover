package com.dydu.hoover.domain.hoover;

import com.dydu.hoover.domain.Move;
import com.dydu.hoover.domain.Rotater;
import com.dydu.hoover.domain.room.TileTypeViewer;

import java.util.Optional;
import java.util.Stack;
import java.util.stream.Stream;

/**
 * A hoover implemented as a tom thumb : it keeps a trail of all moves from the start
 * and apply a simple strategy to go to the next dirty tile, always in the same order
 * If it gets stuck (no more dirty tile around), then it goes back its trail until
 * it finds a new dirty tile to go forward
 * If the trail comes empty, it means that the hoover has come back to its starting point
 * so it then stops
 */
public class BasicHoover extends AbstractHoover {

    private Stack<Move> moveTrail = new Stack<>();

    @Override
    public void initialize(TileTypeViewer viewer) {
        super.initialize(viewer);
        this.moveTrail.clear();
    }

    private Optional<Move> chooseForwardDirection() {
        return Stream.of(Move.LEFT, Move.RIGHT, Move.UP, Move.DOWN)
                .filter(super::isDirty)
                .findFirst();
    }

    @Override
    public Optional<Move> move() {
        Optional<Move> direction = chooseForwardDirection();
        if (direction.isPresent()) {
            moveTrail.add(direction.get());
            return direction;
        } else {
            return moveTrail.empty() ? Optional.empty() : Optional.of(moveTrail.pop()).map(Rotater.turnaround::rotate);
        }
    }

}
