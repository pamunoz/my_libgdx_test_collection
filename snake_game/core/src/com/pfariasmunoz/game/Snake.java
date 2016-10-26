package com.pfariasmunoz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.pfariasmunoz.game.Constants.Direction;

public class Snake {

    public static final String TAG = Snake.class.getName();

    private Direction direction;

    private Vector2 mPosition;
    private Vector2 mVelocity;

    private float mElapsedTime;

    private float mSpeed, mSecondsToSpeedUp, mSecondsToMove;

    private Array<Vector2> tail;

    public Snake() {
        mSpeed = 1.0f;
        mSecondsToSpeedUp = 0;
        mSecondsToMove = 0;
        mPosition = new Vector2();
        tail = new Array<Vector2>();
        tail.add(mPosition);
        direction = Direction.RIGHT;
        mVelocity = new Vector2(Constants.GRID_SIDE, 0);

    }


    public void render(ShapeRenderer renderer) {
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
        mElapsedTime += delta;
        updateDifficulty(delta);
        move(delta);
        updateDirection();
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
            updateSnakePosition(mPosition);
            mSecondsToMove = 0;
        }
    }

    private void updateSnakePosition(Vector2 targetPosition) {
        targetPosition.x += mVelocity.x;
        targetPosition.y += mVelocity.y;
    }

    private void turn(Vector2 directionToTurn) {
        mVelocity.x = directionToTurn.x;
        mVelocity.y = directionToTurn.y;
    }

    private void updateDirection() {
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            switch (direction) {
                case RIGHT:
                    direction = Direction.DONW;
                    turn(Constants.DOWN);
                    break;
                case LEFT:
                    direction = Direction.UP;
                    turn(Constants.UP);
                    break;
                case UP:
                    direction = Direction.RIGHT;
                    turn(Constants.RIGHT);
                    break;
                case DONW:
                    direction = Direction.LEFT;
                    turn(Constants.LEFT);
                    break;
            }
        } else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            switch (direction) {
                case RIGHT:
                    direction = Direction.UP;
                    turn(Constants.UP);
                    break;
                case LEFT:
                    direction = Direction.DONW;
                    turn(Constants.DOWN);
                    break;
                case UP:
                    direction = Direction.LEFT;
                    turn(Constants.LEFT);
                    break;
                case DONW:
                    direction = Direction.RIGHT;
                    turn(Constants.RIGHT);
                    break;
            }
        }
    }

}
