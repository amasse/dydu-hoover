package com.dydu.hoover.domain.hoover;

import com.dydu.hoover.domain.Move;
import com.dydu.hoover.domain.room.TileTypeViewer;

import java.util.Optional;

/**
 * The standard behaviour for any Hoover
 */
public interface Hoover {

    /**
     * Performs all initializations required for this hoover
     * see {@link TileTypeViewer}
     *
     * @param viewer the tile viewer that this hoover needs to be attached to
     */
    void initialize(TileTypeViewer viewer);

    /**
     * Compute next move
     *
     * @return the next move for this hoover
     */
    Optional<Move> move();

}
