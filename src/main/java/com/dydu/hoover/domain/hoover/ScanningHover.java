package com.dydu.hoover.domain.hoover;

import com.dydu.hoover.domain.Move;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScanningHover extends AbstractHoover {

    private Optional<Move> lastMove = Optional.empty();
    private LinkedList<Anchor> anchors = Lists.newLinkedList();
    private Optional<Anchor> currentAnchor = Optional.empty();
    private Set<Anchor> deadPath = Sets.newHashSet();

    @Override
    public Optional<Move> move() {
        Optional<Move> move;
        watchAround();
        if (isDeadEnd()) {
            move = Stream.of(Move.values()).filter(m -> !isWall(m)).findFirst();
        } else {
            move = keepCurrentDirection();
        }
        if (!move.isPresent()) {
            move = moveTowardCurrentAnchor();
        }

        updateState(move);
        lastMove = move;
        return move;

    }

    private Optional<Move> moveTowardCurrentAnchor() {
        Optional<Move> move;
        if (!currentAnchor.isPresent()) {
            currentAnchor = this.anchors.stream().sorted(Anchor.closest()).findFirst();
            currentAnchor.ifPresent(this.anchors::remove);
        }
        move = currentAnchor.map(this::getAllowedMoveFrom).orElse(Optional.empty());
        return move;
    }

    private Optional<Move> keepCurrentDirection() {
        return lastMove.map(this::isDirty).orElse(false) ? lastMove : Optional.empty();
    }

    private Optional<Move> getAllowedMoveFrom(Anchor anchor) {
        List<Move> moveOrder = anchor.getMoveOrder();
        List<Move> forbiddenMoves = Lists.newArrayList();
        for (Move move : moveOrder) {
            Integer count = deadPath.stream().filter(e -> e.equals(new Anchor(move))).findFirst().map(Anchor::getVisitCount).orElse(0);
            if (count >= 2) {
                forbiddenMoves.add(move);
            }
        }
        if (lastMove.isPresent() && forbiddenMoves.isEmpty()) {
            forbiddenMoves.add(Rotater.turnaround.rotate(lastMove.get()));
        }
        moveOrder.removeAll(forbiddenMoves);
        Optional<Move> move = moveOrder.stream().filter(m -> !isWall(m)).findFirst();
        if (!move.isPresent()) {
            deadPath.clear();
            return getAllowedMoveFrom(anchor);
        }
        return move;
    }


    private void updateState(Optional<Move> move) {
        if (move.isPresent()) {
            updateAnchors(move.get());
            updateCurrentAnchor(move.get());
            updateDeadPath(move.get());
        }
    }

    private void updateDeadPath(Move move) {
        if (isDirty(move)) {
            deadPath.clear();
        } else {
            Anchor nextAnchor = new Anchor(move);
            if (!deadPath.contains(nextAnchor)) {
                deadPath.add(nextAnchor);
            }
            for (Anchor anchor : deadPath) {
                anchor.add(move);
            }
            deadPath = Sets.newHashSet(deadPath);
        }

    }

    private void updateCurrentAnchor(Move move) {
        if (currentAnchor.isPresent()) {
            currentAnchor.get().add(move);
            if (currentAnchor.get().isVisited()) {
                currentAnchor = Optional.empty();
            }
        }
    }

    private void updateAnchors(Move move) {
        Iterator<Anchor> iterator = anchors.iterator();
        while (iterator.hasNext()) {
            Anchor anchor = iterator.next();
            anchor.add(move);
            if (anchor.isVisited()) {
                iterator.remove();
            }
        }
    }

    private boolean isDeadEnd() {
        return Stream.of(Move.values()).filter(this::isWall).count() == 3;
    }

    private void watchAround() {
        anchors.addAll(Stream.of(Move.values())
                .filter(this::isDirty)
                .map(Anchor::new).collect(Collectors.toList()));
    }


}
