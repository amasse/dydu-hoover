package com.dydu.hoover.domain.room;

import com.dydu.hoover.domain.Position;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;

import java.util.Set;
import java.util.stream.IntStream;

/**
 * A simple representation of a X/Y referential bounds
 */
public class Bounds {

    public static final int X_ORIGIN = 1;
    public static final int Y_ORIGIN = 1;
    private Range<Integer> xRange;
    private Range<Integer> yRange;

    @VisibleForTesting
    Bounds(Range<Integer> xRange, Range<Integer> yRange) {
        this.xRange = xRange;
        this.yRange = yRange;
    }

    public boolean contains(Position position) {
        return xRange.contains(position.getX()) && yRange.contains(position.getY());
    }

    public static Bounds createPositiveBounds(int xSize, int ySize) {
        return new Bounds(Range.closed(X_ORIGIN, xSize), Range.closed(Y_ORIGIN, ySize));
    }

    private IntStream streamOn(Range<Integer> range) {
        return IntStream.rangeClosed(range.lowerEndpoint(), range.upperEndpoint());
    }

    public Set<Position> allPositionsInBounds() {
        final Set<Position> answer = Sets.newHashSet();
        streamOn(xRange).forEach(i -> streamOn(yRange).forEach(j -> answer.add(new Position(i, j))));
        return answer;
    }

    public Range<Integer> getxRange() {
        return xRange;
    }

    public Range<Integer> getyRange() {
        return yRange;
    }

}
