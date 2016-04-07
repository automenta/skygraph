package spacegraph.test;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
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

    @Override
    public void simpleInitApp() {


        Material defaultMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        defaultMaterial.setColor("Color", ColorRGBA.Gray);
        root.setMaterial(defaultMaterial);

        rootNode.attachChild(root);

        run();
    }
}
