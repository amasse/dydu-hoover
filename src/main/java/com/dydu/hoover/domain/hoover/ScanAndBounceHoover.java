package com.dydu.hoover.domain.hoover;

import com.dydu.hoover.domain.Move;
import com.dydu.hoover.domain.room.TileType;
import com.dydu.hoover.domain.room.TileTypeViewer;

import java.util.Optional;

public class ScanAndBounceHoover extends AbstractHoover {

    private enum Mode {SCAN, BOUNCE}

    private Move move = Move.RIGHT;
    private Mode currentMode = Mode.SCAN;
    private Rotater rotater = new SnakeRotater();
    private Safety safety = new Safety();


    @Override
    public void initialize(TileTypeViewer viewer) {
        super.initialize(viewer);
    }

    @Override
    public Optional<Move> move() {
        Optional<Move> move = currentMode.equals(Mode.SCAN) ? scan() : bounce();
        if (!move.isPresent()) {
            switchMode();
            return move();
        } else {
            safety.add(move.get());
            if (safety.isLocked()) {

            }

        }
        return move;
    }

    private Optional<Move> bounce() {
        Optional<Move> dirty = checkAroundFor(TileType.DIRTY);
        if (dirty.isPresent()) {
            move = dirty.get();
            currentMode = Mode.SCAN;
            return dirty;
        }
        while (isWall(this.move)) {
            this.move = Rotater.clockwise.rotate(this.move);
        }
        return Optional.of(this.move);
    }

    private Optional<Move> checkAroundFor(TileType type) {
        Move check = move;
        for (int i = 0; i < 4; i++) {
            check = Rotater.clockwise.rotate(check);
            if (viewer.previewMove(check).equals(type)) {
                return Optional.of(check);
            }
        }
        return Optional.empty();
    }

    private Optional<Move> scan() {
        for (int i = 0; i < 4; i++) {
            if (isDirty(move)) return Optional.of(move);
            move = rotater.rotate(move);
        }
        return Optional.empty();
    }

    private void switchMode() {
        safety = new Safety();
        currentMode = currentMode == Mode.BOUNCE ? Mode.SCAN : Mode.BOUNCE;
    }


}
