package spacegraph.test;

import com.jme3.math.Vector2f;

/**
 * Created by me on 4/6/16.
 */
public class Vector2  {

    public final Vector2f v;


    public Vector2() {
        this(0, 0);
    }

    public Vector2(float x, float y) {
        this(new Vector2f(x, y));
    }

    public Vector2(Vector2f v) {
        this.v = v;
    }


}
