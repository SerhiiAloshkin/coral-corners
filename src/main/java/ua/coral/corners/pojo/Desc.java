package ua.coral.corners.pojo;

import java.util.Map;

public class Desc {

    private Map<Coordinates, Cell> cells;

    public Desc(final Map<Coordinates, Cell> cells) {
        this.cells = cells;
    }

    public Map<Coordinates, Cell> getCells() {
        return cells;
    }


}
