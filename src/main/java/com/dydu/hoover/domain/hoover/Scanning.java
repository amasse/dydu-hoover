package com.dydu.hoover.domain.hoover;

import com.dydu.hoover.domain.Move;
import com.dydu.hoover.domain.room.TileType;
import com.dydu.hoover.domain.room.TileTypeViewer;

import java.util.Optional;

public class Scanning {

    private enum State {SCANNING, SUCCESS, FAILED}

    private Move scanDirection;
    private Move nextLineDirection;
    private State state;

    public Scanning(Move scanDirection) {
        this.scanDirection = scanDirection;
        this.nextLineDirection = Rotater.clockwise.rotate(scanDirection);
        this.state = State.SCANNING;
    }

    private Scanning(Move scanDirection, Move nextLineDirection) {
        this.state = State.SCANNING;
        this.scanDirection = scanDirection;
        this.nextLineDirection = nextLineDirection;
    }

    public Optional<Move> getNextMove(TileTypeViewer viewer) {
        if (is(State.SCANNING)) {
            if (isAllowed(scanDirection, viewer)) {
                state = State.SCANNING;
                return Optional.of(scanDirection);
            } else if (isAllowed(nextLineDirection, viewer)) {
                state = State.SUCCESS;
                return Optional.of(nextLineDirection);
            } else {
                state = State.FAILED;
            }
        }
        return Optional.empty();
    }

    public Scanning next() {
        switch (state) {
            case SUCCESS:
                return new Scanning(Rotater.turnaround.rotate(scanDirection), nextLineDirection);
            case FAILED:
                return new Scanning(Rotater.turnaround.rotate(Rotater.clockwise.rotate(scanDirection)), Rotater.clockwise.rotate(nextLineDirection));
            default:
                return this;
        }
    }

    private boolean is(State state) {
        return this.state.equals(state);
    }

    private boolean isAllowed(Move move, TileTypeViewer viewer) {
        TileType tileType = viewer.previewMove(move);
        return tileType.equals(TileType.DIRTY);
    }


}
