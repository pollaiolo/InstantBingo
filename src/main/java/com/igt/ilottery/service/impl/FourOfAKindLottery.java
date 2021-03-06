package com.igt.ilottery.service.impl;

import com.igt.ilottery.service.AbstractLottery;
import com.igt.ilottery.service.DrawingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Four of a kind lottery implementation.
 * 4 out of 6 numbers should match to win the prize.
 *
 * @author Francesco Maria Maglia, Ringmaster, f.maglia@ringmaster.it
 */
@Service
public class FourOfAKindLottery extends AbstractLottery {

    private static final int NUMBERS_TO_DRAW = 6;
    private static final int DRAW_BOUNDARY = 6;
    private static final int WINNING_THRESHOLD = 4;

    @Autowired
    public FourOfAKindLottery(DrawingService drawingService) {
        this.drawer = drawingService;
    }

    @Override
    protected List<Integer> draw() {
        return drawer.boundedDraw(NUMBERS_TO_DRAW, DRAW_BOUNDARY);
    }

    @Override
    protected void checkDrawIntegrity(List<Integer> drawnNumbers) {
        if(drawnNumbers.size() != NUMBERS_TO_DRAW || isDrawOutOfBounds(drawnNumbers)) {
            throw new IllegalStateException("Drawing system failure");
        }
    }

    @Override
    protected boolean isWinningDraw(List<Integer> drawnNumbers) {
        List<Integer> frequencyList = new ArrayList<>();
        for(Integer drawnNumber : drawnNumbers) {
            frequencyList.add(Collections.frequency(drawnNumbers, drawnNumber));
        }
        return Collections.max(frequencyList) >= WINNING_THRESHOLD;
    }

    private boolean isDrawOutOfBounds(List<Integer> drawnNumbers) {
        for(Integer i : drawnNumbers) {
            if(i >= DRAW_BOUNDARY) {
                return true;
            }
        }
        return false;
    }
}
