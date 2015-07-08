package com.dydu.hoover.domain.hoover;

import com.dydu.hoover.domain.Move;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class AnchorTest  {

    @Test
    public void creation_move_should_be_the_one_to_go_to_anchor() {
        Anchor anchor = new Anchor(Move.UP);
        assertThat(anchor.isVisited()).isFalse();
        assertThat(anchor.getMoveOrder().get(0)).isEqualTo(Move.UP);
        anchor.add(Move.UP);
        assertThat(anchor.isVisited()).isTrue();

        anchor = new Anchor(Move.RIGHT);
        assertThat(anchor.isVisited()).isFalse();
        assertThat(anchor.getMoveOrder().get(0)).isEqualTo(Move.RIGHT);
        anchor.add(Move.RIGHT);
        assertThat(anchor.isVisited()).isTrue();

        anchor = new Anchor(Move.DOWN);
        assertThat(anchor.isVisited()).isFalse();
        assertThat(anchor.getMoveOrder().get(0)).isEqualTo(Move.DOWN);
        anchor.add(Move.DOWN);
        assertThat(anchor.isVisited()).isTrue();

        anchor = new Anchor(Move.LEFT);
        assertThat(anchor.isVisited()).isFalse();
        assertThat(anchor.getMoveOrder().get(0)).isEqualTo(Move.LEFT);
        anchor.add(Move.LEFT);
        assertThat(anchor.isVisited()).isTrue();
    }

    @Test
    public void added_moves_should_set_the_anchor_further() {
        Anchor anchor = new Anchor(Move.UP);
        anchor.add(Move.DOWN);
        anchor.add(Move.UP);
        anchor.add(Move.UP);
        assertThat(anchor.isVisited()).isTrue();
    }


    @Test
    public void should_respect_the_move_order() {
        Anchor anchor = new Anchor(Move.UP);
        anchor.add(Move.DOWN);
        anchor.add(Move.LEFT);
        anchor.add(Move.LEFT);
        anchor.add(Move.LEFT);
        assertThat(anchor.getMoveOrder().get(0)).isEqualTo(Move.RIGHT);
        assertThat(anchor.getMoveOrder().get(1)).isEqualTo(Move.UP);
    }

    @Test
    public void anchors_should_be_equals() {
        Anchor anchor = new Anchor(Move.UP);
        Anchor anchor2 = new Anchor(Move.UP);
        assertThat(anchor).isEqualTo(anchor2);

        anchor2.add(Move.LEFT);
        assertThat(anchor).isNotEqualTo(anchor2);

        anchor2.add(Move.RIGHT);
        assertThat(anchor).isEqualTo(anchor2);

        anchor.add(Move.UP);
        anchor2.add(Move.UP);
        assertThat(anchor).isEqualTo(anchor2);
    }


}