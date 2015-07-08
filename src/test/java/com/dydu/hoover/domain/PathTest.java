package com.dydu.hoover.domain;

import com.dydu.hoover.utils.Constants;
import com.google.common.base.Joiner;
import junit.framework.TestCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PathTest {


    @Test
    public void test_should_track_positions() {
        Path path = new Path(new Position(1, 1));
        path.register(new Position(1,2));
        path.register(new Position(2, 2));
        path.register(new Position(2, 3));

        assertThat(path.getNumberOfSteps()).isEqualTo(4);

        String stringPath = Joiner.on(Constants.LF).join("[1:1]","[1:2]","[2:2]", "[2:3]");
        assertThat(path.print()).isEqualTo(stringPath);

    }

}