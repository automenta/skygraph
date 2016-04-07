package spacegraph.test;

/**
 * Created by me on 4/6/16.
 */
public class Slider extends RectNode {

    public enum SliderType {
        Horizontal, Vertical
    }

    public final DoubleVar value;
    public final DoubleVar knobLength;
    public final SliderType type;

    public Slider(DoubleVar v, DoubleVar min, DoubleVar max, DoubleVar knobLength, SliderType type) {
        this.type = type;
        this.value = v;
        this.knobLength = knobLength;


    }


    public DoubleVar getKnobLength() {
        return knobLength;
    }

    public DoubleVar getValue() {
        return value;
    }
}
