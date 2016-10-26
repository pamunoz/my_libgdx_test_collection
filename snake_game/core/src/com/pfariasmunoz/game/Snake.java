package com.pfariasmunoz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import com.pfariasmunoz.game.Constants.Direction;

public class Snake {

    public static final String TAG = Snake.class.getName();

    private Direction mCurrentDirection;

    private Vector2 mPosition;
    private Vector2 mVelocity;

    private float initialTime;

    private float mSpeed, mSecondsToSpeedUp, mSecondsToMove;

    private Array<Vector2> tail;

    public Snake() {
        initialTime = TimeUtils.nanoTime();
        mSpeed = 1.0f;
        mSecondsToSpeedUp = 0;
        mSecondsToMove = 0;
        mPosition = new Vector2();
        tail = new Array<Vector2>();
        tail.add(mPosition);
        mCurrentDirection = Direction.RIGHT;
        mVelocity = new Vector2(mCurrentDirection.getDir());

    }


    public void render(ShapeRenderer renderer) {
        float elapsedTime = TimeUtils.nanoTime() - initialTime;

        renderer.set(ShapeType.Filled);
        renderer.setColor(
                Constants.SNAKE_COLOR.r,
                Constants.SNAKE_COLOR.g,
                Constants.SNAKE_COLOR.b, 1);
        for (int i = 0; i < tail.size; i++) {
            renderer.rect(tail.get(i).x, tail.get(i).y,
                    Constants.GRID_SIDE, Constants.GRID_SIDE);
        }

    }

    public void update(float delta) {
        updateDifficulty(delta);
        move(delta);
        mCurrentDirection.toString();
    }

    private void updateDifficulty(float delta) {
        mSecondsToSpeedUp += delta;
        if (mSecondsToSpeedUp > Constants.DIFFICULTY_ACCELEROMETER) {
            mSpeed *= Constants.SNAKE_ACCELERATION;
            mSecondsToSpeedUp = 0;
        }

    }

    private void move(float delta) {
        mSecondsToMove += delta;
        if (mSecondsToMove > 1.0f / mSpeed) {
            //updateSnakePosition(mPosition);
            mPosition.x += mCurrentDirection.getDir().x;
            mPosition.y += mCurrentDirection.getDir().y;
            ensureInBounds();
            mSecondsToMove = 0;
        }
    }



    private void turn(Vector2 vector) {
        mVelocity.x = vector.x;
        mVelocity.y = vector.y;
    }

    private void updateDirection() {
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            switch (mCurrentDirection) {
                case RIGHT:
                    mCurrentDirection = Direction.DOWN;
                    break;
                case LEFT:
                    mCurrentDirection = Direction.UP;
                    break;
                case UP:
                    mCurrentDirection = Direction.RIGHT;
                    break;
                case DOWN:
                    mCurrentDirection = Direction.LEFT;
            }
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            switch (mCurrentDirection) {
                case RIGHT:
                    mCurrentDirection = Direction.UP;
                    break;
                case LEFT:
                    mCurrentDirection = Direction.DOWN;
                    break;
                case UP:
                    mCurrentDirection = Direction.LEFT;
                    break;
                case DOWN:
                    mCurrentDirection = Direction.RIGHT;
            }
        }
        ensureInBounds();
    }


    private void ensureInBounds() {
        if (mPosition.x > Constants.COLUMNS * Constants.GRID_SIDE - Constants.GRID_SIDE) {
            mPosition.x = Constants.COLUMNS * Constants.GRID_SIDE - Constants.GRID_SIDE;
        } else if (mPosition.x < 0) {
            mPosition.x = 0;
        }

        if (mPosition.y > Constants.COLUMNS * Constants.GRID_SIDE - Constants.GRID_SIDE) {
            mPosition.y = Constants.COLUMNS * Constants.GRID_SIDE - Constants.GRID_SIDE;
        } else if (mPosition.y < 0) {
            mPosition.y = 0;
        }
    }

}
