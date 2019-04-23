package rummikub;

import javax.swing.*;

public class Game {
    private JLayeredPane panel;
    private Grid grid;

    public Game(JLayeredPane panel){
        this.panel = panel;
        this.grid = new Grid(50);
    }
}