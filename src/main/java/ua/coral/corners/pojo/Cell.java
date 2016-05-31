package ua.coral.corners.pojo;

import java.util.HashMap;
import java.util.Map;

import static ua.coral.corners.engine.Constants.MAX_LENGTH;
import static ua.coral.corners.pojo.ChipType.BLACK;
import static ua.coral.corners.pojo.ChipType.BLUE;
import static ua.coral.corners.pojo.ChipType.EMPTY;
import static ua.coral.corners.pojo.ChipType.GREEN;
import static ua.coral.corners.pojo.ChipType.WHITE;

public class Cell implements Cloneable {

    private Chip chip;
    private Coordinates coordinates;
    private CellValue cellValue;
    private boolean selected;

    public static Cell valueOf(final Coordinates coordinates) {
        return CellsCache.map.get(coordinates.hashCode());
    }

    private Cell(final Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Chip getChip() {
        return chip;
    }

    public void setChip(final Chip chip) {
        this.chip = chip;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public CellValue getCellValue() {
        return cellValue;
    }

    public void setCellValue(final CellValue cellValue) {
        this.cellValue = cellValue;
    }

    public boolean isEmpty() {
        return chip.getChipType() == EMPTY;
    }

    public boolean isGreen() {
        return chip.getChipType() == GREEN;
    }

    public boolean isBlack() {
        return chip.getChipType() == BLACK;
    }

    public boolean isBlue() {
        return chip.getChipType() == BLUE;
    }

    public boolean isWhite() {
        return chip.getChipType() == WHITE;
    }

    public boolean isChiped() {
        return isBlack() || isWhite();
    }

    public int getTop() {
        return 17 + (coordinates.getVIndex() * 59);
    }

    public int getLeft() {
        return 17 + (coordinates.getHIndex() * 59);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(final boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "chip=" + chip +
                ", coordinates=" + coordinates +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Cell cell = (Cell) super.clone();
        cell.setCellValue(getCellValue());
        cell.setChip(getChip());
        cell.setSelected(isSelected());
        cell.setCoordinates(getCoordinates());
        return cell;
    }

    private static class CellsCache {

        private static final Map<Integer, Cell> map = new HashMap<>(MAX_LENGTH * 2);

        static {
            for (int h = 0; h < MAX_LENGTH; h++) {
                for (int v = 0; v < MAX_LENGTH; v++) {
                    Coordinates coordinates = Coordinates.valueOf(h, v);
                    map.put(coordinates.hashCode(), new Cell(coordinates));
                }
            }
        }

        private CellsCache() {
            super();
        }
    }
}
