package com.comp2042.model.bricks.shape;

import com.comp2042.model.bricks.BrickGenerator;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Implements a brick generator that produces random brick shapes.
 */
public class RandomBrickGenerator implements BrickGenerator {

    private final List<Brick> brickList;


    private static final Map<Integer, PropBrick> propBrickMap = new HashMap<>();

    static {
        AreaClearBrick areaClearBrick = new AreaClearBrick();
        propBrickMap.put(areaClearBrick.PropBrickValue(), areaClearBrick);
    }

    private final Deque<Brick> nextBricks = new ArrayDeque<>();

    /**
     * Initializes the generator and preloading the first two random bricks into the queue.
     */
    public RandomBrickGenerator() {
        brickList = new ArrayList<>();
        brickList.add(new IBrick());
        brickList.add(new JBrick());
        brickList.add(new LBrick());
        brickList.add(new OBrick());
        brickList.add(new SBrick());
        brickList.add(new TBrick());
        brickList.add(new ZBrick());
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
    }

    /**
     * Gets a {@code PropBrick} instance based on its color code.
     * @param value color code of the are clear brick
     * @return {@code PropBrick} object
     */
    public static PropBrick getPropBrick(int value){
        return propBrickMap.get(value);
    }

    /**
     * Gets the next brick from the front of the queue and removes it.
     * If the queue size drops below 2 after removal, a new random brick is generated and added to the back.
     * @return The {@code Brick} object that is falling
     */
    @Override
    public Brick getBrick() {
        if (nextBricks.size() <= 1) {
            nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
            if (ThreadLocalRandom.current().nextInt(brickList.size() * 10) == brickList.size()){
                if (true) {
                    int i = ThreadLocalRandom.current().nextInt(propBrickMap.size());
                    nextBricks.add(propBrickMap.values()
                            .stream()
                            .skip(i)
                            .findFirst()
                            .orElse(null));
                }
            }
        }
        return nextBricks.poll();
    }

    /**
     * Gets the brick that is next in line to fall without removing it from the queue.
     * @return the {@code Brick} object at the head of the preview queue
     */
    @Override
    public Brick getNextBrick() {
        return nextBricks.peek();
    }
}
