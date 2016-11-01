package com.pfariasmunoz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Snake.java
 * Purpose: Creates a Snake Object which is the principal character of this game.
 *
 * @author Pablo Muñoz.
 * @version 1.0 10/07/2016
 */

public class Snake extends InputAdapter {

    public static final String TAG = Snake.class.getName();
    private ObjectIntMap<String> dirMap;
    private Vector2 mPosition;
    private int mDirIndex;
    private Array<Vector2> mTail;
    private Array<Vector2> mDirection;
    private Viewport mViewport;

    /**
     * The contructor of the snake object.
     *
     * @param viewport the Viewport necessary to the position of the snake, and its side.
     */
    public Snake(Viewport viewport) {
        dirMap = new ObjectIntMap<String>();
        mViewport = viewport;
        init();
    }

    /**
     * Initializes the snake object with its directions of movement and its position.
     */
    public void init() {
        dirMap.put("UP", 0);
        dirMap.put("DOWN", 1);
        dirMap.put("RIGHT", 2);
        dirMap.put("LEFT", 3);
        mDirIndex = 2;
        mTail = new Array<Vector2>();
        mDirection = new Array<Vector2>();
        mDirection.add(Constants.UP);
        mDirection.add(Constants.DOWN);
        mDirection.add(Constants.RIGHT);
        mDirection.add(Constants.LEFT);
        mPosition = new Vector2(0, mViewport.getWorldHeight() - Constants.BLOCK_SIZE);
        mTail.add(mPosition);
    }

    /**
     * Render the Snake object to the screen.
     *
     * @param renderer the ShapeRenderer that draw the snake.
     */
    public void render(ShapeRenderer renderer) {
        renderer.set(ShapeType.Filled);
        renderer.setColor(0.545f, 0.545f, 0.964f, 1.0f);
        for (int i = 0; i < mTail.size; i++) {
            renderer.rect(mTail.get(i).x, mTail.get(i).y,
                    Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
        }
    }

    /**
     * Add a new block to the snake object if it eats food.
     */
    public void addBlock() {
        // add to the current new position the direction specified by the index
        float x = mTail.first().x + mDirection.get(mDirIndex).x;
        float y = mTail.first().y + mDirection.get(mDirIndex).y;
        Vector2 newPosition = new Vector2(x, y);
        mTail.insert(0, newPosition);
    }

    /**
     * Check whether the snake object has eaten food.
     *
     * @param foodPosition the position of the food for comparing with the position of the snake.
     * @return a boolean if the snake object has eaten.
     */
    public boolean hasEaten(Vector2 foodPosition) {
        // if the positions of ether vector are equal in a given distance (epsilon)
        return mTail.first().epsilonEquals(foodPosition, 0.2f);
    }

    /**
     * Removes the last element of the array of the snake sections.
     */
    public void removeLastElement() {
        mTail.pop();
    }

    /**
     * Checks if the snake fulfill the condition to be dead.
     *
     * @return the boolean value if the snake is dead or not.
     */
    public boolean isDead() {
        return leaveBounds() || eatsItsTail();
    }

    /**
     * Convenience method to test if the snake leave the borders.
     *
     * @return  the boolean value if the snake leaves the bounds of not.
     */
    private boolean leaveBounds() {
        boolean answer = false;
        Vector2 snakeHead = mTail.first();
        answer = snakeHead.x < 0 || snakeHead.y < 0 ||
                snakeHead.x >= Constants.WORLD_WIDTH ||
                snakeHead.y >= Constants.WORLD_HEIGHT;
        return answer;

    }

    /**
     * Checks if the head of the snake collides with one of its sections.
     *
     * @return the bolean value if the snake collide with itself or not.
     */
    private boolean eatsItsTail() {
        boolean answer = false;

        if (mTail.size > 1) {
            for (int i = 1; i < mTail.size; i++) {
                if (mTail.first().x == mTail.get(i).x && mTail.first().y == mTail.get(i).y) {
                    answer = true;
                }
            }
        }

        return answer;
    }

    /**
     * checks for the user inputs of the user for the snake to turn in each direction.
     */
    public void update() {

        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            if (mTail.size == 1) {
                mDirIndex = dirMap.get("RIGHT", 2);
            } else {
                mDirIndex = mDirIndex != dirMap.get("LEFT", 3) ? dirMap.get("RIGHT", 2) : dirMap.get("LEFT", 3);
            }
        } else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            if (mTail.size == 1) {
                mDirIndex = dirMap.get("LEFT", 3);
            } else {
                mDirIndex = mDirIndex != dirMap.get("RIGHT", 2) ? dirMap.get("LEFT", 3) : dirMap.get("RIGHT", 2);
            }
        } else if (Gdx.input.isKeyPressed(Keys.UP)) {
            if (mTail.size == 1) {
                mDirIndex = dirMap.get("UP", 0);
            } else {
                mDirIndex = mDirIndex != dirMap.get("DOWN", 1) ? dirMap.get("UP", 0) : dirMap.get("DOWN", 1);
            }
        } else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            if (mTail.size == 1) {
                mDirIndex = dirMap.get("DOWN", 1);
            } else {
                mDirIndex = mDirIndex != dirMap.get("UP", 0) ? dirMap.get("DOWN", 1) : dirMap.get("UP", 0);
            }
        } else if (isDead()) {
            if (Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
                init();
            }
        }
    }

    /**
     * Check if ether segment of the snake object collide with other element.
     *
     * @param obstacle the Vector2 that is the position of the element to check collision.
     * @return the bolean value if the segment is colliding with another element of the game.
     */
    public boolean isColliding(Vector2 obstacle) {
        boolean answer = false;

        if (mTail.size > 1) {
            for (Vector2 snakeSegment : mTail) {
                if (snakeSegment.epsilonEquals(obstacle, 0.2f)) {
                    answer = true;
                }
            }
        }
        return answer;
    }

    /**
     * Gives the score according with the numbers of items eaten by the snake object.
     *
     * @return returns the the length - 1 of the array of sections of the snake.
     */
    public int getScore() {
        return mTail.size -1;
    }

    /**
     * Turn counter clockwise the snake object.
     */
    public void turnCounterClockWise() {
        int newIndex = dirMap.get("RIGHT", 2);
        switch (mDirIndex) {
            case 2:
                newIndex = dirMap.get("UP", 0);
                break;
            case 3:
                newIndex = dirMap.get("DOWN", 1);
                break;
            case 0:
                newIndex = dirMap.get("LEFT", 3);
                break;
            case 1:
                newIndex = dirMap.get("RIGHT", 2);
                break;
        }
        mDirIndex = newIndex;
    }

    /**
     * Turn clockwise the snake object.
     */
    public void turnClockWise() {
        int newIndex = 2;
        switch (mDirIndex) {
            case 2:
                newIndex = 1;
                break;
            case 3:
                newIndex = 0;
                break;
            case 0:
                newIndex = 2;
                break;
            case 1:
                newIndex = 3;
                break;
        }
        mDirIndex = newIndex;
    }

}
