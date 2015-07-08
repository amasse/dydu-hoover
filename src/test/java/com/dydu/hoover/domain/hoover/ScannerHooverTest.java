package com.dydu.hoover.domain.hoover;

import com.dydu.hoover.domain.Path;
import com.dydu.hoover.domain.Position;
import com.dydu.hoover.domain.RoomCleaner;
import com.dydu.hoover.domain.room.RectangleRoom;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ScannerHooverTest {

    @Test
    public void test_should_clean_basic_maze() {
        Hoover hoover = new ScanningHover();
        RectangleRoom room = RectangleRoom.buildFrom("/the_simplest.txt");
        Path path = new RoomCleaner(hoover, room, new Position(2,2)).clean();
        System.out.println("it took " + path.getNumberOfSteps() + " steps to complete the do you room");
        assertThat(room.isClean()).isTrue();
    }

    @Test
    public void test_should_clean_a_small_square() {
        Hoover hoover = new ScanningHover();
        RectangleRoom room = RectangleRoom.buildFrom("/the_small_square.txt");
        Path path = new RoomCleaner(hoover, room, new Position(2,2)).clean();
        System.out.println("it took " + path.getNumberOfSteps() + " steps to complete the do you room");
        assertThat(room.isClean()).isTrue();
    }

    @Test
    public void test_should_clean_the_simple() {
        Hoover hoover = new ScanningHover();
        RectangleRoom room = RectangleRoom.buildFrom("/the_simple.txt");
        Path path = new RoomCleaner(hoover, room, new Position(2,2)).clean();
        System.out.println("it took " + path.getNumberOfSteps() + " steps to complete the do you room");
        assertThat(room.isClean()).isTrue();
    }

    @Test
    public void test_should_clean_the_way_back() {
        Hoover hoover = new ScanningHover();
        RectangleRoom room = RectangleRoom.buildFrom("/the_way_back.txt");
        Path path = new RoomCleaner(hoover, room, new Position(5,5)).clean();
        System.out.println("it took " + path.getNumberOfSteps() + " steps to complete the do you room");
        assertThat(room.isClean()).isTrue();
    }



    @Test
    public void test_should_clean_dydu_maze() {
        Hoover hoover = new ScanningHover();
        RectangleRoom room = RectangleRoom.buildFrom("/hoover1.txt");
        Path path = new RoomCleaner(hoover, room, new Position(3,2)).clean();
        System.out.println("it took " + path.getNumberOfSteps() + " steps to complete the do you room");
        assertThat(room.isClean()).isTrue();
    }

    @Test
    public void test_should_clean_the_big_square() {
        Hoover hoover = new ScanningHover();
        RectangleRoom room = RectangleRoom.buildFrom("/the_big_square.txt");
        Path path = new RoomCleaner(hoover, room).clean();
        System.out.println("it took " + path.getNumberOfSteps() + " steps to complete the big square room");
        assertThat(room.isClean()).isTrue();
    }

    @Test
    public void test_should_clean_the_circle() {
        Hoover hoover = new ScanningHover();
        RectangleRoom room = RectangleRoom.buildFrom("/the_circle.txt");
        Path path = new RoomCleaner(hoover, room).clean();
        System.out.println("it took " + path.getNumberOfSteps() + " steps to complete the circle room");
        assertThat(room.isClean()).isTrue();
    }

    @Test
    public void test_should_clean_the_random() {
        Hoover hoover = new ScanningHover();
        RectangleRoom room = RectangleRoom.buildFrom("/the_hardest.txt");
        Path path = new RoomCleaner(hoover, room).clean();
        System.out.println("it took " + path.getNumberOfSteps() + " steps to complete the random room");
        System.out.println("remaining dirty : " + room.getDirtyPositions());
        assertThat(room.isClean()).isTrue();
    }

    @Test
    public void test_should_clean_the_vicious() {
        Hoover hoover = new ScanningHover();
        RectangleRoom room = RectangleRoom.buildFrom("/the_vicious.txt");
        Path path = new RoomCleaner(hoover, room).clean();
        System.out.println("it took " + path.getNumberOfSteps() + " steps to complete the vicious room");
        assertThat(room.isClean()).isTrue();
    }


    @Test
    public void test_should_clean_the_cross() {
            Hoover hoover = new ScanningHover();
            RectangleRoom room = RectangleRoom.buildFrom("/the_cross.txt");
            Path path = new RoomCleaner(hoover, room).clean();
            System.out.println("it took " + path.getNumberOfSteps() + " steps to complete the vicious room");
            assertThat(room.isClean()).isTrue();

    }

    @Test
    public void test_should_clean_the_subject() {
        List<Integer> counts = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            Hoover hoover = new ScanningHover();
            RectangleRoom room = RectangleRoom.buildFrom("/the_subject.txt");
            Path path = new RoomCleaner(hoover, room).clean();
            System.out.println("it took " + path.getNumberOfSteps() + " steps to complete the subject room");
            counts.add(path.getNumberOfSteps());
            assertThat(room.isClean()).isTrue();
        }
        System.out.println("Average efficiency of hover is " + counts.stream().collect(Collectors.averagingInt(m -> m)));

    }

}