package spacegraph.test;

import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
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

    public void span(double v, double v1, double v2, double v3) {

    }


}
