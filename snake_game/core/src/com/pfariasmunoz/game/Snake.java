package com.pfariasmunoz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import com.badlogic.gdx.utils.viewport.Viewport;
import com.pfariasmunoz.game.Constants.Direction;

public class Snake {

    public static final String TAG = Snake.class.getName();

    private Vector2 mPosition;
    private Direction mDirection;
    private int mDirIndex;

    private float mDeltaElapsed;

    private Array<Vector2> mTail;
    private Vector2 mSnakeHead;

    private Viewport viewport;

    public Snake(Viewport viewport) {
        mDirIndex = 2;
        mTail = new Array<Vector2>();
        mSnakeHead = mTail.get(0);
        this.viewport = viewport;
        init();
    }

    /**
     * reset the snake in its fist position.
     */
    public void init() {
        mDeltaElapsed = 0;
        mPosition = new Vector2(0, viewport.getWorldHeight() - Constants.BLOCK_SIZE);
        mTail.add(mPosition);
    }

    public Array<Vector2> getTail() {
        return mTail;
    }

    /**
     * Draw the snake to the screen.
     * @param renderer the ShapeRenderer that draw the figure.
     */
    public void render(ShapeRenderer renderer) {
        renderer.set(ShapeType.Filled);
        renderer.setColor(0.8f, 0.8f, 1.0f, 1.0f);
        for (int i = 0; i < mTail.size; i++) {
            renderer.rect(mTail.get(i).x, mTail.get(i).y,
                    Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
        }
    }



    public void addBlock() {
        Vector2 snakeHead = mTail.get(0);
        Vector2 newDirection = mDirection.values()[mDirIndex].getDir();

        // the new position of the next block added to the snake tail
        Vector2 newPosition = new Vector2(snakeHead.x + newDirection.x, snakeHead.y + newDirection.y);
        // the new block with the new position is added as the head
        mTail.insert(0, newPosition);
    }

    public Vector2 getSnakeHead() {
        return mSnakeHead;
    }

    public void setSnakeHead(Vector2 mSnakeHead) {
        this.mSnakeHead = mSnakeHead;
    }

    /**
     *
     * @param snakePosition
     * @param foodPosition
     * @return
     */
    public boolean hasEaten(Vector2 snakePosition, Vector2 foodPosition) {
        // if the positions of ether vector are equal in a given distance (epsilon)
        return snakePosition.epsilonEquals(foodPosition, 0.2f);
    }

    public void removeLastElement() {
        mTail.pop();
    }

    /**
     * This checks the condition is the snake is dead.
     * @return weather the snake has leave the bounds of the screen or has eaten its tail.
     */
    public boolean isDead() {
        return leaveBounds() || eatsItsTail();
    }

    /**
     * This checks the condition if the has crash in the borders.
     * @return weather the snake crash or not.
     */
    private boolean leaveBounds() {
        Vector2 snakeHead = mTail.get(0);
        if (snakeHead.x < 0 || snakeHead.y < 0 || snakeHead.x >= Constants.WORLD_WIDTH || snakeHead.y >= Constants.WORLD_HEIGHT) {
            return true;
        } else {
            return false;
        }
    }

    private boolean eatsItsTail() {
        Vector2 snakeHead = mTail.get(0);

        for (int i = 0; i < mTail.size; i++) {
            if (snakeHead.epsilonEquals(mTail.get(i), 0.01f)) {
                return true;
            }
        }
        return false;
    }

}
