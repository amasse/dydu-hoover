package com.dydu.hoover.domain.room;

import com.dydu.hoover.domain.Position;
import com.dydu.hoover.utils.Constants;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * A simple representation of a dirty rectangle room that needs to be cleaned
 * It keeps track of all tiles composing the room, and keeps the bounds for convenience
 * Note that the room is stateful regarding its tiles
 *
 */
public class RectangleRoom {

    private Map<Position, TileType> tiles;

    private Bounds bounds;

    @VisibleForTesting
    RectangleRoom(Map<Position, TileType> tiles, Bounds bounds) {
        this.tiles = tiles;
        this.bounds = bounds;
    }

    /**
     * @return all the positions of the dirty tiles remaining in the room
     */
    public List<Position> getDirtyPositions() {
        return this.tiles.entrySet().stream()
                .filter(e -> e.getValue().equals(TileType.DIRTY))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Get the state of the tile at a particular position
     *
     * @param position the position of the tile type to retrieve
     * @return the tile type (wall if not found in the definition)
     */
    public TileType getTileAt(final Position position) {
        return Optional.ofNullable(tiles.get(position)).orElse(TileType.WALL);
    }

    /**
     * @param position The position of the tile
     * @return true if the position is dirty, false otherwise
     */
    public boolean isCleanable(Position position) {
        return getTileAt(position).equals(TileType.DIRTY);
    }


    /**
     * Check whether the room still contains dirty tiles
     *
     * @return true if the room is clean, false otherwise
     */
    public boolean isClean() {
        return !tiles.values().stream().anyMatch(tile -> tile.equals(TileType.DIRTY));
    }


    /**
     * Clean a position if required. Do nothing on already cleaned tiles
     * Trying to clean a wall raises an exception
     * @param position the position of the tile to clean
     */
    public void clean(Position position) {
        Preconditions.checkArgument(!getTileAt(position).equals(TileType.WALL));
        if (isCleanable(position)) {
            System.out.println("Cleaning position : " + position);
            tiles.put(position, TileType.CLEAN);
        }
    }

    /**
     * Print the maze, from top to bottom, in the format used in the exercice context
     */
    public String print(Position hooverPosition) {
        StringWriter writer = new StringWriter();
        for (int i = bounds.getyRange().upperEndpoint(); i >= bounds.getyRange().lowerEndpoint(); i--) {
            for (int j = bounds.getxRange().lowerEndpoint(); j <= bounds.getxRange().upperEndpoint(); j++) {
                if (hooverPosition != null && hooverPosition.getX() == j && hooverPosition.getY() == i) {
                    writer.write("O");
                } else {
                    writer.write(getTileAt(new Position(j, i)).equals(TileType.WALL) ? Constants.WALL_STRING : " ");

                }
            }
            writer.write(Constants.LF);
        }
        return writer.toString();
    }


    /**
     * Create a maze directly from a file (should be in the classpath)
     *
     * @param file the name of the file to read
     * @return dirty maze
     */
    public static RectangleRoom buildFrom(String file) {
        InputStream stream = RectangleRoom.class.getResourceAsStream(file);
        try {
            Map<Position, TileType> tiles = Maps.newHashMap();
            List<String> lines = CharStreams.readLines(new InputStreamReader(stream));
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                for (int j = 0; j < line.length(); j++) {
                    tiles.put(new Position(j + 1, lines.size() - i), TileType.getFor(line.charAt(j)));
                }
            }
            Bounds bounds = Bounds.createPositiveBounds(lines.get(0).length(), lines.size());
            return new RectangleRoom(tiles, bounds);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get randomly a dirty position
     * @return A dirty position
     */
    public Position getRandomDirtyPosition() {
        List<Position> startPositions = getDirtyPositions();
        return startPositions.get(Math.abs(new Random().nextInt()) % (startPositions.size()));
    }

    /**
     * Create a maze from an input matrix as provided by {@link com.dydu.hoover.utils.MatrixFileReader}
     *
     * @param matrix the matrix read by the reader
     * @return dirty Room
     */
    public static RectangleRoom buildFrom(String[][] matrix) {
        Bounds bounds = Bounds.createPositiveBounds(matrix[0].length - 1, matrix.length - 1);
        Map<Position, TileType> tiles = Maps.newHashMap();
        bounds.allPositionsInBounds().forEach(
                p -> tiles.put(p, TileType.getFor(matrix[p.getY()][p.getX()].toCharArray()[0]))
        );
        return new RectangleRoom(tiles, bounds);
    }



}
