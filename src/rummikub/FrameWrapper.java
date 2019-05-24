package rummikub;

import rummikub.interfaces.WindowLocation;

import java.awt.*;

/** O objetivo dessa classe é diminuir o coupling entre Game e Pedra **/
public class FrameWrapper implements WindowLocation {
    private Frame frame;

    private FrameWrapper(){}

    public FrameWrapper(Frame frame){
        this.frame = frame;
    }

    @Override
    public Point getLocation() {
        return frame.getLocation();
    }
}
