package com.dydu.hoover.domain.room;

import com.dydu.hoover.domain.Position;
import junit.framework.TestCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BoundsTest {

    @Test
    public void test_create_positive_bounds() throws Exception {
        Bounds bounds = Bounds.createPositiveBounds(3, 3);
        assertThat(bounds.getxRange().lowerEndpoint()).isEqualTo(1);
        assertThat(bounds.getxRange().upperEndpoint()).isEqualTo(3);
        assertThat(bounds.getyRange().lowerEndpoint()).isEqualTo(1);
        assertThat(bounds.getyRange().upperEndpoint()).isEqualTo(3);
    }

    @Test
    public void test_generate_all_positions() throws Exception {
        Bounds bounds = Bounds.createPositiveBounds(3, 3);
        assertThat(bounds.allPositionsInBounds()).containsOnly(
                new Position(1, 1),
                new Position(2, 1),
                new Position(3, 1),
                new Position(1, 2),
                new Position(2, 2),
                new Position(3, 2),
                new Position(1, 3),
                new Position(2, 3),
                new Position(3, 3)
        );
    }

    @Test
    public void test_contains_position() {
        Bounds bounds = Bounds.createPositiveBounds(3, 3);
        assertThat(bounds.contains(new Position(2,1))).isTrue(); // OK
        assertThat(bounds.contains(new Position(2,4))).isFalse(); // Y too big
        assertThat(bounds.contains(new Position(4,1))).isFalse(); // X too big
        assertThat(bounds.contains(new Position(-1,1))).isFalse(); // X negative
        assertThat(bounds.contains(new Position(1,-1))).isFalse(); // Y negative
    }

}