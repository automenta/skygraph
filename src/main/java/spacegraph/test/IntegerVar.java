package spacegraph.test;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by me on 4/6/16.
 */
public class IntegerVar extends SimpleIntegerProperty {

    public IntegerVar(int i) {
        super(i);
    }

    public final int i() { return intValue(); }
    public final double d() { return doubleValue(); }
}
