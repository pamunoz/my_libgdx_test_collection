package com.pfariasmunoz.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Pablo Farias on 20-10-16.
 */

public class Constants {
    // Setting up the screen with its grid
    public static final float BLOCK_SIZE = 16;
    public static final float COLUMNS = 40;
    public static final float ROWS = 40;
    public static final float WORLD_WIDTH = BLOCK_SIZE * COLUMNS;
    public static final float WORLD_HEIGHT = BLOCK_SIZE * ROWS;
    public static final Color BACKGROUND_COLOR = new Color(0.3764705882f, 0.3764705882f, 0.3764705882f, 1.0f);


    public enum Direction {
        DOWN(new Vector2(0, -Constants.BLOCK_SIZE)),
        UP(new Vector2(0, Constants.BLOCK_SIZE)),
        RIGHT(new Vector2(Constants.BLOCK_SIZE, 0)),
        LEFT(new Vector2(-Constants.BLOCK_SIZE, 0));


        private Vector2 dir;
        Direction(Vector2 dir) {
            this.dir = dir;
        }

        public Vector2 getDir() {
            return dir;
        }


        @Override
        public String toString() {
            switch (this) {
                case RIGHT:
                    System.out.println("Direction Right: " + getDir());
                    break;
                case LEFT:
                    System.out.println("Direction Left: " + getDir());
                    break;
                case UP:
                    System.out.println("Direction Up: " + getDir());
                    break;
                case DOWN:
                    System.out.println("Direction Down: " + getDir());
            }
            return super.toString();
        }
    }

}
