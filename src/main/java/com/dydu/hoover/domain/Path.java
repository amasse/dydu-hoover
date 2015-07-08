package com.dydu.hoover.domain;

import com.dydu.hoover.utils.Constants;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.io.StringWriter;
import java.util.List;

/**
 * The path keeps record of a sequence of position, and can print it as required
 */
public class Path {
    private List<Position> positions;

    public Path(Position start) {
        positions = Lists.newLinkedList();
        register(start);
    }

    public void register(Position position) {
        System.out.println(position);
        positions.add(position);
    }

    public int getNumberOfSteps() {
        return positions.size();
    }

    public String print() {
        return Joiner.on(Constants.LF).join(positions);
    }

    @Override
    public String toString() {
        return print();
    }
}
