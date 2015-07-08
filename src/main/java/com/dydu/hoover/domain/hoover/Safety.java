package com.dydu.hoover.domain.hoover;

import com.dydu.hoover.domain.Move;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.LinkedList;
import java.util.Map;


class Safety {
    private LinkedList<Move> moves = Lists.newLinkedList();
    private Map<Move, Integer> moveCount = Maps.newHashMap();

    public void add(Move move) {
        moveCount.put(move, moveCount.getOrDefault(move, 0) + 1);
        if (moves.isEmpty() || !moves.getFirst().equals(move)) {
            if (moves.size() == 4) {
                Move last = moves.removeLast();
                moveCount.put(last, 0);
            }
            moves.addFirst(move);
        }
    }

    public void reinit() {
        moves = Lists.newLinkedList();
        moveCount = Maps.newHashMap();
    }

    public boolean isLocked() {
        return isCircular(moves) &&
                (moveCount.getOrDefault(Move.RIGHT, 0).equals(moveCount.getOrDefault(Move.LEFT, 0))) &&
                (moveCount.getOrDefault(Move.UP, 0).equals(moveCount.getOrDefault(Move.DOWN, 0)));
    }

    private boolean isCircular(LinkedList<Move> moves) {
        if (moves.size() == 4) {
            if (Sets.newHashSet(moves).size() == 2) {
                return true;
            }
        }
        boolean circularClockwise = true;
        boolean circularCounterClockwise = true;
        for (int i = 0; i < moves.size(); i++) {
            int next = (i + 1) % moves.size();
            circularClockwise = Rotater.clockwise.rotate(moves.get(i)).equals(moves.get(next)) && circularClockwise;
            circularCounterClockwise = Rotater.counterClockwise.rotate(moves.get(i)).equals(moves.get(next)) && circularCounterClockwise;
        }
        return circularClockwise || circularCounterClockwise;
    }

    public void print() {
        moves.forEach(System.out::println);
        moveCount.forEach((k, v) -> System.out.println(k + " : " + v));
    }


}
