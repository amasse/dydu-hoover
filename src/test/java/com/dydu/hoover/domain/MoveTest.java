package com.dydu.hoover.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MoveTest {

    @Test
    public void test_should_move_right() {
        Position initial = new Position(1,1);
        assertThat(Move.RIGHT.from(initial)).isEqualTo(new Position(2,1));
    }

    @Test
    public void test_should_move_left() {
        Position initial = new Position(1,1);
        assertThat(Move.LEFT.from(initial)).isEqualTo(new Position(0,1));
    }

    @Test
    public void test_should_move_up() {
        Position initial = new Position(1,1);
        assertThat(Move.UP.from(initial)).isEqualTo(new Position(1,2));
    }

    @Test
    public void test_should_move_down() {
        Position initial = new Position(1,1);
        assertThat(Move.DOWN.from(initial)).isEqualTo(new Position(1,0));
    }

}