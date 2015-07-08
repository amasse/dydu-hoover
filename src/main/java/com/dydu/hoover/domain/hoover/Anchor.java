package com.dydu.hoover.domain.hoover;

import com.dydu.hoover.domain.Move;
import com.dydu.hoover.domain.Position;
import com.google.common.collect.Lists;

import java.security.SecureRandom;
import java.util.Comparator;
import java.util.List;

public class Anchor {

    private int verticalDistance = 0;
    private int horizontalDistance = 0;
    private int id;
    private int visitCount;

    public Anchor(Move move) {
        id = new SecureRandom().nextInt();
        horizontalDistance -= move.from(new Position(0, 0)).getX();
        verticalDistance -= move.from(new Position(0, 0)).getY();
    }

    public void add(Move move) {
        horizontalDistance += move.from(new Position(0, 0)).getX();
        verticalDistance += move.from(new Position(0, 0)).getY();
        if (verticalDistance == 0 && horizontalDistance == 0) {
            visitCount ++;
        }
    }

    public boolean isVisited() {
        return visitCount > 0;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public boolean isVisitedIf(Move move) {
        int nextH = horizontalDistance - move.from(new Position(0, 0)).getX();
        int nextV = verticalDistance - move.from(new Position(0, 0)).getY();
        return nextH == 0 && nextV == 0;
    }

    public List<Move> getMoveOrder() {
        List<Move> moves = Lists.newArrayList();


        if (Math.abs(horizontalDistance) > Math.abs(verticalDistance)) {
            moves.add(horizontalDistance > 0 ? Move.LEFT : Move.RIGHT);
            moves.add(verticalDistance > 0 ? Move.DOWN : Move.UP);
            moves.add(horizontalDistance <= 0 ? Move.LEFT : Move.RIGHT);
            moves.add(verticalDistance <= 0 ? Move.DOWN : Move.UP);
        } else {
            moves.add(verticalDistance > 0 ? Move.DOWN : Move.UP);
            moves.add(horizontalDistance > 0 ? Move.LEFT : Move.RIGHT);
            moves.add(verticalDistance <= 0 ? Move.DOWN : Move.UP);
            moves.add(horizontalDistance <= 0 ? Move.LEFT : Move.RIGHT);
        }
        return moves;
    }

    public static Comparator<? super Anchor> closest() {
        return ((a, b) -> (Math.abs(a.horizontalDistance) + Math.abs(a.verticalDistance)) - (Math.abs(b.horizontalDistance) + Math.abs(b.verticalDistance)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Anchor anchor = (Anchor) o;

        if (verticalDistance != anchor.verticalDistance) return false;
        return horizontalDistance == anchor.horizontalDistance;

    }

    @Override
    public int hashCode() {
        int result = verticalDistance;
        result = 31 * result + horizontalDistance;
        return result;
    }

    @Override
    public String toString() {
        return "Anchor{" +
                "id=" + id +
                ", verticalDistance=" + verticalDistance +
                ", horizontalDistance=" + horizontalDistance +
                ", visitCount=" + visitCount +
                '}';
    }
}
