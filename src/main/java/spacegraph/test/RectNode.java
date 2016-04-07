package spacegraph.test;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;

/**
 * Created by me on 4/6/16.
 */
public class RectNode extends Node {


    //final private Quaternion rotation = new Quaternion();
    private Quad quadNode;

    public RectNode() {
        super();

        add(new Geometry("q", quadNode = new Quad(1,1)));

    }

    public <X extends Spatial> X add(X child) {
        attachChild(child);
        return child;
    }

    public boolean remove(Spatial removed) {
        return detachChild(removed)!=-1;
    }

    public void clear() {
        detachAllChildren();
    }

    public void tangible(boolean b) {

    }

    @Override
    protected void updateWorldBound() {
        super.updateWorldBound();
        System.out.println(this + " " + getWorldBound());
    }

    public void span(double x1, double y1, double x2, double y2) {
        float cx = (float)(x1+x2)/2f;
        float cy = (float)(y1+y2)/2f;
        move(cx, cy, 0);

        float w = (float)Math.abs(x2-x1);
        float h = (float)Math.abs(y2-y1);
        scale(w, h);

    }


    public void visible(boolean b) {
        setCullHint(b ? CullHint.Inherit : CullHint.Always);
    }

    public void scale(double x, double y) {
        setLocalScale((float)x, (float)y, 1);
    }

    public void move(double x, double y, double z) {
        setLocalTranslation((float)x, (float)y, (float)z);
    }
}
