package com.dydu.hoover.domain.room;

import com.dydu.hoover.domain.Move;

/**
 * Something that offers the preview {@link TileType} for a {@link Move}
 */
public interface TileTypeViewer {

    /**
     * Gives the destination {@link TileType} if the {@link Move} is done
     *
     * @param move : the intended move
     * @return the type of the tile that this move lands on
     */
    TileType previewMove(Move move);

}
