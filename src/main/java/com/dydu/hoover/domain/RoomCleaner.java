package com.dydu.hoover.domain;

import com.dydu.hoover.domain.hoover.BasicHoover;
import com.dydu.hoover.domain.hoover.Hoover;
import com.dydu.hoover.domain.room.RectangleRoom;
import com.dydu.hoover.domain.room.TileType;
import com.dydu.hoover.domain.room.TileTypeViewer;

import java.util.Optional;

/**
 * The orchestrator class that allows a {@link Hoover} to clean a {@link RectangleRoom}
 */
public class RoomCleaner implements TileTypeViewer {

    private Hoover hoover;

    private RectangleRoom room;

    private Position hooverPosition;

    public RoomCleaner(Hoover hoover, RectangleRoom room) {
        this(hoover, room, room.getRandomDirtyPosition());
    }

    public RoomCleaner(Hoover hoover, RectangleRoom room, Position hooverPosition) {
        this.hoover = hoover;
        this.room = room;
        this.hooverPosition = hooverPosition;
    }

    /**
     * Main method to clean the registered room with the registered hoover
     * The cleaning stops if the room has been fully cleaned or if the hoover stops
     * A naive limit is computed to prevent from infinite looping :
     * (square value of dirty tiles : if the hoover takes more than that to complete, it's shitty)
     *
     * @return Path : the path taken by the hoover to clean the room
     */
    public Path clean() {
        Path path = new Path(hooverPosition);
        hoover.initialize(this);
        room.clean(hooverPosition);
        int limit = this.room.getDirtyPositions().size() * this.room.getDirtyPositions().size();
        int loopCount = 0;
        do {
            loopCount++;
            Optional<Move> move = hoover.move();
            if (move.isPresent()) {
                hooverPosition = move.get().from(hooverPosition);
                room.clean(hooverPosition);
                path.register(hooverPosition);
            } else {
                break;
            }
        } while (!room.isClean() && loopCount < limit);
        return path;
    }

    @Override
    public TileType previewMove(Move move) {
        return room.getTileAt(move.from(hooverPosition));
    }


    /**
     * Main method to execute a hoover on a file given as first parameter
     *
     * @param args : file name for the maze
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Please provide a file name for the room");
        }
        Hoover hoover = new BasicHoover();
        RectangleRoom room = RectangleRoom.buildFrom(args[0]);
        Path path = new RoomCleaner(hoover, room).clean();
        System.out.println(path.print());
    }

}
