package com.pfariasmunoz.simplesnake;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Pablo Farias on 28-10-16.
 */

public class Snake {
    private static final Array<Vector2> mDirections = new Array<Vector2>();
    private final Array<Vector2> mTail;


    public Snake() {
        mDirections.add(Constants.DOWN);
        mDirections.add(Constants.UP);
        mDirections.add(Constants.RIGHT);
        mDirections.add(Constants.LEFT);

        mTail = new Array<Vector2>();

    }

    public void removeLast() {
        mTail.removeIndex(0);
    }

    public void removeHead() {
        mTail.pop();
    }


}
