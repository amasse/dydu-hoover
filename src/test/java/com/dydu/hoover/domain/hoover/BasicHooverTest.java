package com.dydu.hoover.domain.hoover;

import com.dydu.hoover.domain.RoomCleaner;
import com.dydu.hoover.domain.Path;
import com.dydu.hoover.domain.room.RectangleRoom;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class BasicHooverTest  {

    @Test
    public void should_clean_dydu_maze() {
        Hoover hoover = new BasicHoover();
        RectangleRoom room = RectangleRoom.buildFrom("/hoover1.txt");
        Path path = new RoomCleaner(hoover, room).clean();
        System.out.println("it took " + path.getNumberOfSteps() + " steps to complete the do you room");
        assertThat(room.isClean()).isTrue();
    }

    @Test
    public void should_clean_the_big_square() {
        Hoover hoover = new BasicHoover();
        RectangleRoom room = RectangleRoom.buildFrom("/the_big_square.txt");
        Path path = new RoomCleaner(hoover, room).clean();
        System.out.println("it took " + path.getNumberOfSteps() + " steps to complete the big square room");
        assertThat(room.isClean()).isTrue();
    }

    @Test
    public void should_clean_the_circle() {
        Hoover hoover = new BasicHoover();
        RectangleRoom room = RectangleRoom.buildFrom("/the_circle.txt");
        Path path = new RoomCleaner(hoover, room).clean();
        System.out.println("it took " + path.getNumberOfSteps() + " steps to complete the circle room");
        assertThat(room.isClean()).isTrue();
    }

    @Test
    public void should_clean_the_random() {
        Hoover hoover = new BasicHoover();
        RectangleRoom room = RectangleRoom.buildFrom("/the_hardest.txt");
        Path path = new RoomCleaner(hoover, room).clean();
        System.out.println("it took " + path.getNumberOfSteps() + " steps to complete the random room");
        assertThat(room.isClean()).isTrue();
    }

    @Test
    public void should_clean_the_vicious() {
        Hoover hoover = new BasicHoover();
        RectangleRoom room = RectangleRoom.buildFrom("/the_vicious.txt");
        Path path = new RoomCleaner(hoover, room).clean();
        System.out.println("it took " + path.getNumberOfSteps() + " steps to complete the vicious room");
        assertThat(room.isClean()).isTrue();
    }

    @Test
    public void should_clean_the_cross() {
        Hoover hoover = new BasicHoover();
        RectangleRoom room = RectangleRoom.buildFrom("/the_cross.txt");
        Path path = new RoomCleaner(hoover, room).clean();
        System.out.println("it took " + path.getNumberOfSteps() + " steps to complete the cross room");
        assertThat(room.isClean()).isTrue();
    }

    @Test
    public void should_clean_the_subject() {
        List<Integer> counts = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            Hoover hoover = new BasicHoover();
            RectangleRoom room = RectangleRoom.buildFrom("/the_subject.txt");
            Path path = new RoomCleaner(hoover, room).clean();
            System.out.println("it took " + path.getNumberOfSteps() + " steps to complete the subject room");
            counts.add(path.getNumberOfSteps());
            assertThat(room.isClean()).isTrue();
        }
        System.out.println("Average efficiency of hover is " + counts.stream().collect(Collectors.averagingInt(m -> m)));
    }


}