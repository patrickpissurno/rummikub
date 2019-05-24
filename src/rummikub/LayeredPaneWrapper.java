package rummikub;

import rummikub.interfaces.MoveToFront;

import javax.swing.*;
import java.awt.*;

/** O objetivo dessa classe é diminuir o coupling entre Game e Pedra **/
public class LayeredPaneWrapper implements MoveToFront {
    private JLayeredPane panel;

    private LayeredPaneWrapper(){}

    public LayeredPaneWrapper(JLayeredPane panel){
        this.panel = panel;
    }

    @Override
    public void moveToFront(JLabel label) {
        for(Component c : panel.getComponents()){
            if(!c.equals(label))
                panel.setLayer(c, JLayeredPane.DEFAULT_LAYER);
        }
        panel.setLayer(label, JLayeredPane.DEFAULT_LAYER + 1);
    }
}
