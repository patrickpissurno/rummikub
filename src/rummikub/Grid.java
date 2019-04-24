package rummikub;

public class Grid {
    private int cellSizeInPx;

    /**
     * @param cellSizeInPx tamanho da célula em pixels
     */
    public Grid(int cellSizeInPx){
        this.cellSizeInPx = cellSizeInPx;
    }


    /**
     * @return tamanho da célula em pixels
     */
    public int getCellSizeInPx() {
        return cellSizeInPx;
    }

    /**
     * transforma o valor de uma coordenada (x ou y) em um valor pertencente ao grid
     * @param coordinate coordenada x ou y em pixels
     * @return coordenada do grid
     */
    public int snapCoordinateToGrid(float coordinate){
        return Math.round(coordinate / cellSizeInPx) * cellSizeInPx;
    }
}
