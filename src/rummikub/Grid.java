package rummikub;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private int cellSizeInPx;
    private List<Pedra> mesa;

    public Grid(int cellSizeInPx){
        this.cellSizeInPx = cellSizeInPx;
        this.mesa = new ArrayList<>();
    }

    public int getCellSizeInPx() {
        return cellSizeInPx;
    }

    public List<Pedra> getMesa() {
        return new ArrayList<>(this.mesa);
    }

    public void addPedra(Pedra pedra){
        this.mesa.add(pedra);
    }

    public int snapCoordinateToGrid(float coordinate){
        return Math.round(coordinate / cellSizeInPx) * cellSizeInPx;
    }
}
