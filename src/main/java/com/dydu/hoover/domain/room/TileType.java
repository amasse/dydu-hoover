package com.dydu.hoover.domain.room;

import com.dydu.hoover.utils.Constants;

/**
 * The type of a tile
 */
public enum TileType {
    WALL, DIRTY, CLEAN;

    public static TileType getFor(char token) {
        return token == Constants.WALL_CHAR ? WALL : DIRTY;
    }
}
