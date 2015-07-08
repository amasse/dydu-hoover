package com.dydu.hoover.domain.hoover;

import com.dydu.hoover.domain.Move;
import com.dydu.hoover.domain.room.TileTypeViewer;

import java.util.Optional;
import java.util.stream.Stream;

public class ScannerHoover extends AbstractHoover {

    private Move scanDirection = Move.LEFT;
    private int rotateCount;
    private boolean rotateRight;
    private final static int THRESHOLD = 10;
    private Rotater rotation;

    @Override
    public void initialize(TileTypeViewer viewer) {
        super.initialize(viewer);
        rotation = Rotater.clockwise;
        rotateRight =true;
    }

    @Override
    public Optional<Move> move() {
        if (isDirty(scanDirection)) {
            return Optional.of(scanDirection);
        }
        Optional<Move> dirtyAround = Stream.of(Move.values())
                .filter(super::isDirty)
                .findFirst();
        if (dirtyAround.isPresent()) {
            return dirtyAround;
        }
        if (!isWall(rotation.rotate(scanDirection))) {
            rotateCount ++;
            scanDirection = rotation.rotate(scanDirection);
            if (rotateCount > THRESHOLD) {
                rotation = rotateRight ? Rotater.counterClockwise : Rotater.clockwise;
                rotateRight = !rotateRight;
                rotateCount = 0;
            }
            return Optional.of(scanDirection);
        }
        if (isClean(scanDirection)) {
            return Optional.of(scanDirection);
        }
        scanDirection = Rotater.turnaround.rotate(scanDirection);
        return move();
    }



}
