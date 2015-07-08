package com.dydu.hoover.domain.hoover;

import com.dydu.hoover.domain.Move;
import com.dydu.hoover.domain.room.TileType;
import com.dydu.hoover.domain.room.TileTypeViewer;

/**
 * A basic abstract class for all {@link Hoover}, that stores the {@link TileTypeViewer}
 * and provides shortcuts methods to preview a move
 */
public abstract class AbstractHoover implements Hoover {

    protected TileTypeViewer viewer;

    @Override
    public void initialize(TileTypeViewer viewer) {
        this.viewer = viewer;
    }

    protected final boolean isWall(Move move) {
        return viewer.previewMove(move).equals(TileType.WALL);
    }
    protected final boolean isClean(Move move) {
        return viewer.previewMove(move).equals(TileType.CLEAN);
    }
    protected final boolean isDirty(Move move) {
        return viewer.previewMove(move).equals(TileType.DIRTY);
    }
}
