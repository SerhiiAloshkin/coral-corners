package ua.coral.corners.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.coral.corners.pojo.Cell;
import ua.coral.corners.pojo.Chip;
import ua.coral.corners.pojo.Coordinates;
import ua.coral.corners.pojo.Desc;

import java.util.LinkedHashMap;
import java.util.Map;

import static ua.coral.corners.engine.Constants.MAX_LENGTH;
import static ua.coral.corners.pojo.ChipType.BLACK;
import static ua.coral.corners.pojo.ChipType.WHITE;

@Service
public class DescService {

    @Autowired
    private CoordinatesService coordinatesService;
    @Autowired
    private CellService cellService;
    @Autowired
    private ValueService valueService;

    private Desc desc;

    public Desc getDesc() {
        if (desc == null) {
            final Map<Coordinates, Cell> cells = new LinkedHashMap<>();
            for (int v = 0; v < MAX_LENGTH; v++) {
                for (int h = 0; h < MAX_LENGTH; h++) {
                    cells.put(coordinatesService.getCoordinates(h, v), cellService.getCell(h, v));
                }
            }
            desc = new Desc(cells);
        }
        return desc;
    }

    public Cell getCell(final Coordinates coordinates) {
        return getDesc().getCells().get(coordinates);
    }

    public void setChipToCell(final Coordinates coordinates, final Chip chip) {
        getDesc().getCells().get(coordinates).setChip(chip);
    }

    public String getBlackValue() {
        return String.valueOf(valueService.getValue(BLACK, getDesc().getCells()));
    }

    public String getWhiteValue() {
        return String.valueOf(valueService.getValue(WHITE, getDesc().getCells()));
    }

    public boolean isEmptyCell(final Coordinates coordinates) {
        if (getDesc().getCells().containsKey(coordinates)) {
            return getDesc().getCells().get(coordinates).isEmpty();
        }
        return false;
    }


}
