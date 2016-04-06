package spacegraph.test;

import javafx.beans.property.SimpleDoubleProperty;


public class DoubleVar extends SimpleDoubleProperty {

    public DoubleVar(double d) {
        super(d);
    }
    public final int i() { return intValue(); }
    public final double d() { return doubleValue(); }
}
