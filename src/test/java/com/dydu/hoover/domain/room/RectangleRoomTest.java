package com.dydu.hoover.domain.room;

import com.dydu.hoover.domain.Position;
import com.dydu.hoover.utils.MatrixFileReader;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.HashMap;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class RectangleRoomTest  {

    private RectangleRoom buildRoom() {
        HashMap<Position, TileType> map = Maps.newHashMap();
        map.put(new Position(2,2), TileType.DIRTY);
        map.put(new Position(2,3), TileType.DIRTY);
        map.put(new Position(3,2), TileType.DIRTY);
        map.put(new Position(3,3), TileType.DIRTY);
        return new RectangleRoom(map, Bounds.createPositiveBounds(4,4));
    }

    @Test
    public void test_a_dirty_tile_can_be_cleaned() throws Exception {
        RectangleRoom room = buildRoom();
        Position position = new Position(2, 2);
        assertThat(room.getTileAt(position)).isEqualTo(TileType.DIRTY);
        assertThat(room.isCleanable(position)).isTrue();

        room.clean(position);

        assertThat(room.getTileAt(position)).isEqualTo(TileType.CLEAN);
        assertThat(room.isCleanable(position)).isFalse();
    }


    @Test
    public void test_room_should_give_dirty_tiles_list() throws Exception {
        RectangleRoom room = buildRoom();
        assertThat(room.getDirtyPositions()).containsOnly(
                new Position(2, 2),
                new Position(2, 3),
                new Position(3, 2),
                new Position(3, 3)
        );

        room.clean(new Position(2, 2));
        room.clean(new Position(2, 3));

        assertThat(room.getDirtyPositions()).containsOnly(
                new Position(3, 2),
                new Position(3, 3)
        );
    }


    @Test
    public void test_a_wall_is_not_cleanable() throws Exception {
        RectangleRoom room = buildRoom();
        assertThat(room.isCleanable(new Position(1, 1))).isFalse();

    }

    @Test
    public void test_random_dirty_position_should_be_provided() throws Exception {
        RectangleRoom room = buildRoom();
        Set<Position> positionSet = Sets.newHashSet();
        for (int i = 0; i <= 100; i++) {
            Position position = room.getRandomDirtyPosition();
            positionSet.add(position);
            assertThat(room.getTileAt(position)).isEqualTo(TileType.DIRTY);
        }

        assertThat(positionSet).contains(
                new Position(2, 2),
                new Position(2, 3),
                new Position(3, 2),
                new Position(3, 3)
        );
    }


    @Test
    public void test_a_room_should_be_build_from_file() throws Exception {
        RectangleRoom room = RectangleRoom.buildFrom("/maze1.txt");
        assertThat(room.getDirtyPositions()).containsOnly(
                new Position(1, 3),
                new Position(2, 3),
                new Position(2, 2),
                new Position(3, 2),
                new Position(4, 2),
                new Position(4, 3),
                new Position(5, 3),
                new Position(6, 3),
                new Position(6, 2),
                new Position(7, 2)
        );
    }

    @Test
    public void test_a_room_should_be_build_from_a_matrix() throws Exception {
        String[][] matrix = new MatrixFileReader().readFile("/maze1.txt");
        RectangleRoom room = RectangleRoom.buildFrom(matrix);
        assertThat(room.getDirtyPositions()).containsOnly(
                new Position(1, 3),
                new Position(2, 3),
                new Position(2, 2),
                new Position(3, 2),
                new Position(4, 2),
                new Position(4, 3),
                new Position(5, 3),
                new Position(6, 3),
                new Position(6, 2),
                new Position(7, 2)
        );
    }
}