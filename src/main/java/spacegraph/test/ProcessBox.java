package spacegraph.test;

import com.jme3.app.SimpleApplication;
import com.jme3.scene.Node;

/**
 * Created by me on 4/6/16.
 */
abstract public class ProcessBox extends SimpleApplication implements Runnable {

    public final Node root;

    public ProcessBox() {
        this(new Node());
    }

    public ProcessBox(Node root) {
        super();
        this.root = root;
    }
}
