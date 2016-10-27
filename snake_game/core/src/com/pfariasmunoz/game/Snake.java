package com.pfariasmunoz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import com.pfariasmunoz.game.Constants.Direction;

public class Snake {

    public static final String TAG = Snake.class.getName();

    private Vector2 mPosition;
    private Vector2 mDirection;

    private float mSpeed, mSecondsToSpeedUp, mSecondsToMove;

    private Array<Vector2> tail;

    public Snake() {
        mDirection = new Vector2(Constants.GRID_SIDE, 0);
        mPosition = new Vector2();
        mSpeed = 1.0f;
        mSecondsToSpeedUp = 0;
        mSecondsToMove = 0;
        tail = new Array<Vector2>();
        init();
    }

    public void init() {
        tail.add(mPosition);
    }

    public void render(ShapeRenderer renderer) {

        renderer.set(ShapeType.Filled);
        renderer.setColor(
                Constants.SNAKE_COLOR.r,
                Constants.SNAKE_COLOR.g,
                Constants.SNAKE_COLOR.b, 1);
//        for (int i = 0; i < tail.size; i++) {
//            renderer.rect(tail.get(i).x, tail.get(i).y,
//                    Constants.GRID_SIDE, Constants.GRID_SIDE);
//        }
        renderer.rect(mPosition.x, mPosition.y, Constants.GRID_SIDE, Constants.GRID_SIDE);
    }

    public void update(float delta) {
        move(delta);
        updateDirection();
        //updateDifficulty(delta);
    }

    private void updateDifficulty(float delta) {
        mSecondsToSpeedUp += delta;
        if (mSecondsToSpeedUp > Constants.DIFFICULTY_ACCELEROMETER) {
            mSpeed *= Constants.SNAKE_ACCELERATION;
            mSecondsToSpeedUp = 0;
        }

    }

    public void move(float delta) {
        mSecondsToMove += delta;
        if (mSecondsToMove > 1.0f / mSpeed) {
            //updateSnakePosition(mPosition);
            mPosition.add(mDirection);
            ensureInBounds();
            mSecondsToMove = 0;
        }
    }





    private void updateDirection() {
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            // if snake is going to the right move down
            if (mDirection.x > 0) {
                mDirection = new Vector2(0, -Constants.GRID_SIDE);
            }
            // if snake is going to the left move up
            if (mDirection.x < 0) {
                mDirection = new Vector2(0, Constants.GRID_SIDE);
            }
            // if snake is going up move right
            if (mDirection.y > 0) {
                mDirection = new Vector2(Constants.GRID_SIDE, 0);
            }
            // if snake is going down move left
            if (mDirection.y < 0) {
                mDirection = new Vector2(-Constants.GRID_SIDE, 0);
            }
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            // if snake is going to the right move up
            if (mDirection.x > 0) {
                mDirection = new Vector2(0, Constants.GRID_SIDE);
            }
            // if snake is going to the left move down
            if (mDirection.x < 0) {
                mDirection = new Vector2(0, -Constants.GRID_SIDE);
            }
            // if snake is going up move left
            if (mDirection.y > 0) {
                mDirection = new Vector2(-Constants.GRID_SIDE, 0);
            }
            // if snake is going down move right
            if (mDirection.y < 0) {
                mDirection = new Vector2(Constants.GRID_SIDE, 0);
            }
        }
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
