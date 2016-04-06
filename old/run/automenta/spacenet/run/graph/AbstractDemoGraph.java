/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automenta.spacenet.run.graph;

import automenta.spacenet.Starts;
import automenta.spacenet.plugin.comm.Message;
import automenta.spacenet.plugin.comm.twitter.TwitterGrapher;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.run.communication.content.MessageWindow;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.control.keyboard.IfKeyReleased;
import automenta.spacenet.space.control.keyboard.Keyboard;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.object.graph.GraphBox;
import automenta.spacenet.space.object.graph.arrange.ForceDirectedGraphArranger;
import automenta.spacenet.space.object.graph.arrange.ForceDirectedGraphArranger.ForceDirectedParameters;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.object.widget.button.Button;
import automenta.spacenet.space.object.widget.button.ButtonAction;
import automenta.spacenet.space.object.widget.button.TextButton;
import automenta.spacenet.space.object.widget.panel.Panel;
import automenta.spacenet.space.object.widget.text.TextEditRect;
import automenta.spacenet.space.object.widget.window.Window;
import automenta.spacenet.var.graph.DiGraph;
import automenta.spacenet.var.number.BooleanVar;
import automenta.spacenet.var.string.StringVar;
import automenta.spacenet.var.vector.Vector3;
import com.jme.input.KeyInput;
import org.apache.commons.collections15.Transformer;

abstract public class AbstractDemoGraph extends Box implements Starts {

    private DiGraph g;


    public static class MessageToMessageWindow implements Transformer<Object, Box> {

        @Override public Box transform(Object o) {
            return new MessageWindow((Message) o, 25);
        }
    }
    public static class StringToTextEditWindow implements Transformer<Object, Box> {

        @Override public Box transform(Object o) {
            StringVar sv = (StringVar) o;
            Window w = new Window();
            TextEditRect ter = w.add(new TextEditRect(sv));
            ter.scale(0.9);
            ter.moveDZ(0.1);
            return w;
        }
    }

    public static class SpaceToSpaceWindow implements Transformer<Object, Box> {

        @Override public Box transform(Object o) {
            Box b = (Box) o;
            Window w = new Window();
            w.add(b);
            b.moveDZ(0.1);
            b.scale(0.9);
            return w;
            
        }
        
    }
    public static class ForceDirectedControlPanel extends Panel {

        private final ForceDirectedGraphArranger fd;

        public ForceDirectedControlPanel(final ForceDirectedGraphArranger fd) {
            super();
            this.fd = fd;

            add(new TextRect("FD Controller").span(-0.5, 0.5, 0.5, 0.4));
            
            TextButton expandButton = add(new TextButton("+"));
            expandButton.addButtonAction(new ButtonAction() {
                @Override public void onButtonPressed(Button b) {
                    //fd.getLengthFactor().set(1.25 * fd.getLengthFactor().d());

                    fd.getRepulsion().set(1.25 * fd.getRepulsion().d());
                    fd.getStiffness().set(0.75 * fd.getStiffness().d());

                }
            });
            expandButton.span(-0.5, -0.5, 0, 0);

            TextButton contractButton = add(new TextButton("-"));
            contractButton.addButtonAction(new ButtonAction() {
                @Override public void onButtonPressed(Button b) {
                    //fd.getLengthFactor().set(0.75 * fd.getLengthFactor().d());

                    fd.getRepulsion().set(0.75 * fd.getRepulsion().d());
                    fd.getStiffness().set(1.25 * fd.getStiffness().d());

                }
            });
            contractButton.span(0.5, -0.5, 0, 0);

        }


    }
    

    @Override public void start() {
        Keyboard keyboard = getThe(Keyboard.class);

        g = new DiGraph();

        initGraph(g);
//        TwitterGrapher twitter = add(new TwitterGrapher(g));
//        //twitter.getProfile("...");
//        twitter.getPublicTimeline();


        ForceDirectedParameters params = new ForceDirectedParameters(new Vector3(50, 50, 50), 0.9, 0.01, 0.1);
        ForceDirectedGraphArranger fd = new ForceDirectedGraphArranger(params, 0.1, 0.01, 0.25);
        fd.setFaceCamera(true);

        TypeSpatializer sp = new TypeSpatializer();
        sp.add(Space.class, new SpaceToSpaceWindow());
        sp.add(StringVar.class, new StringToTextEditWindow());
        sp.add(Message.class, new MessageToMessageWindow());

        add(new GraphBox(g, sp, fd));

        g.addVertex(new ForceDirectedControlPanel( fd ) );

        add(new IfKeyReleased(keyboard, KeyInput.KEY_INSERT) {
            @Override public void afterKeyReleased(BooleanVar key) {
                newTextBox();
            }
        });
    }

    public void newTextBox() {
        g.addVertex(new StringVar("text"));
    }

    @Override public void stop() {
    }

    abstract protected void initGraph(DiGraph g);

//    public static void main(String[] args) {
//        new DefaultJmeWindow(new DemoTwitterGraph());
//    }
}
