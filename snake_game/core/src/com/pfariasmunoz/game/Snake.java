package com.pfariasmunoz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Snake {

    public static final String TAG = Snake.class.getName();

    private Vector2 mPosition;
    private int mDirIndex;

    private Array<Vector2> mTail;
    private Array<Vector2> mDirection;

    private Viewport viewport;

    public Snake(Viewport viewport) {
        this.viewport = viewport;
        init();
    }

    public void init() {
        mDirIndex = 2;
        mTail = new Array<Vector2>();
        mDirection = new Array<Vector2>();
        mDirection.add(Constants.UP);
        mDirection.add(Constants.DOWN);
        mDirection.add(Constants.RIGHT);
        mDirection.add(Constants.LEFT);
        mPosition = new Vector2(0, 0);
        mTail.add(mPosition);
    }

    public void render(ShapeRenderer renderer) {
        renderer.set(ShapeType.Filled);
        renderer.setColor(0.8f, 0.8f, 1.0f, 1.0f);
        for (int i = 0; i < mTail.size; i++) {
            renderer.rect(mTail.get(i).x, mTail.get(i).y,
                    Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
        }
    }

    public void addBlock() {
        // add to the current new position the direction specified by the index
        float x = mTail.first().x + mDirection.get(mDirIndex).x;
        float y = mTail.first().y + mDirection.get(mDirIndex).y;
        Vector2 newPosition = new Vector2(x, y);
        mTail.insert(0, newPosition);
    }

    public boolean hasEaten(Vector2 foodPosition) {
        // if the positions of ether vector are equal in a given distance (epsilon)
        return mTail.first().epsilonEquals(foodPosition, 0.2f);
    }

    public void removeLastElement() {
        mTail.pop();
    }

    public boolean isDead() {
        return leaveBounds() || eatsItsTail();
    }

    private boolean leaveBounds() {
        boolean answer = false;
        Vector2 snakeHead = mTail.first();
        answer = snakeHead.x < 0 || snakeHead.y < 0 ||
                snakeHead.x >= Constants.WORLD_WIDTH ||
                snakeHead.y >= Constants.WORLD_HEIGHT;
        return answer;

    }

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

    public void update() {

        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            if (mTail.size == 1) {
                mDirIndex = 2;
            } else {
                mDirIndex = mDirIndex != 3 ? 2 : 3;
            }
        } else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            if (mTail.size == 1) {
                mDirIndex = 3;
            } else {
                mDirIndex = mDirIndex != 2 ? 3 : 2;
            }
        } else if (Gdx.input.isKeyPressed(Keys.UP)) {
            if (mTail.size == 1) {
                mDirIndex = 0;
            } else {
                mDirIndex = mDirIndex != 1 ? 0 : 1;
            }
        } else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            if (mTail.size == 1) {
                mDirIndex = 1;
            } else {
                mDirIndex = mDirIndex != 0 ? 1 : 0;
            }
        }
    }

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

    public int getSize() {
        return mTail.size;
    }

}
