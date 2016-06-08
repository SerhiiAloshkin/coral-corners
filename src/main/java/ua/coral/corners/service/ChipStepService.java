package ua.coral.corners.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.coral.corners.pojo.Cell;
import ua.coral.corners.pojo.Coordinates;
import ua.coral.corners.pojo.CoordinatesSpot;
import ua.coral.corners.pojo.StepSpotValue;

import java.util.Collections;
import java.util.Map;
import java.util.Stack;

@Service
public class ChipStepService {

    private static final Logger LOG = Logger.getLogger(ChipStepService.class);
    @Autowired
    private MoveService moveService;
    @Autowired
    private ValueService valueService;
    private Map<Coordinates, Cell> cells = Collections.emptyMap();
    private Coordinates from;
    private StepSpotValue spotValue;

    public void selectPotentialCells(final Coordinates coordinates, final Map<Coordinates, Cell> cells, final StepSpotValue spotValue) {
        this.from = coordinates;
        this.cells = cells;
        this.spotValue = spotValue;
        moveToForward();
        selectPotentialCell(coordinates, -1, 0);
        selectPotentialCell(coordinates, 1, 0);
        selectPotentialCell(coordinates, 0, -1);
        selectPotentialCell(coordinates, 0, 1);
        moveToBack();
    }

    private void moveToForward() {
        if (spotValue.getSpot() != null) {
            Stack<CoordinatesSpot> spotStack = new Stack<>();
            spotStack.push(spotValue.getSpot());
            StepSpotValue previousValue = spotValue.getPreviousValue();
            while (previousValue != null && previousValue.getSpot() != null) {
                spotStack.push(previousValue.getSpot());
                previousValue = previousValue.getPreviousValue();
            }
            while (!spotStack.isEmpty()) {
                CoordinatesSpot spot = spotStack.pop();
                moveService.move(cells, spot.getFrom(), spot.getTo());
            }
        }
    }

    private void moveToBack() {
        if (spotValue.getSpot() != null) {
            CoordinatesSpot spot = spotValue.getSpot();
            moveService.move(cells, spot.getTo(), spot.getFrom());
            StepSpotValue previousValue = spotValue.getPreviousValue();
            while (previousValue != null && previousValue.getSpot() != null) {
                spot = previousValue.getSpot();
                moveService.move(cells, spot.getTo(), spot.getFrom());
                previousValue = previousValue.getPreviousValue();
            }
        }
    }

    void selectPotentialCell(final Coordinates coordinates, final int hStep, final int vStep) {
        Cell cell = selectPotentialCell(coordinates.getHIndex() + hStep, coordinates.getVIndex() + vStep);
        if (cell != null && !spotValue.getNextSpots().contains(getCoordinatesSpot(cell))) {
            cell = selectPotentialCell(coordinates.getHIndex() + (hStep * 2), coordinates.getVIndex() + (vStep * 2));
            selectNextPotentialCell(cell);
        }
    }

    Cell selectPotentialCell(final int hIndex, final int vIndex) {
        if (!Coordinates.isAvailable(hIndex, vIndex)) {
            return null;
        }
        final Coordinates coord = Coordinates.valueOf(hIndex, vIndex);
        final Cell cell = cells.get(coord);
        final Cell fromCell = cells.get(from);
        if (cell != null && cell.isEmpty() && fromCell.isChiped()) {
            moveAndBack(cell);
        }
        return cell;
    }

    private void moveAndBack(Cell cell) {
        moveService.move(cells, from, cell.getCoordinates());
        addingSpotValueToList(cell);
        moveService.move(cells, cell.getCoordinates(), from);
    }

    private void addingSpotValueToList(Cell cell) {
        int value = valueService.getValue(cell.getChip().getChipType(), cells);
        StepSpotValue spotValue = new StepSpotValue();
//        spotValue.setCells(cells);
        spotValue.setSpot(getCoordinatesSpot(cell));
        spotValue.setValue(value);
        if (this.spotValue.getValue() != null) {
            spotValue.setPreviousValue(this.spotValue);
        }
        this.spotValue.getNextSpots().putIfAbsent(spotValue);
    }

    void selectNextPotentialCells(final Coordinates coordinates) {
        selectNextPotentialCell(coordinates, -1, 0);
        selectNextPotentialCell(coordinates, 1, 0);
        selectNextPotentialCell(coordinates, 0, -1);
        selectNextPotentialCell(coordinates, 0, 1);
    }

    void selectNextPotentialCell(final Coordinates coordinates, final int hStep, final int vStep) {
        Cell cell = cells.get(Coordinates.valueOf(coordinates.getHIndex() + hStep, coordinates.getVIndex() + vStep));
        if (cell != null && (cell.isBlack() || cell.isWhite())) {
            cell = cells.get(Coordinates.valueOf(coordinates.getHIndex() + (hStep * 2), coordinates.getVIndex() + (vStep * 2)));
            if (cell != null && !spotValue.getNextSpots().contains(getCoordinatesSpot(cell))) {
                cell = selectPotentialCell(coordinates.getHIndex() + (hStep * 2), coordinates.getVIndex() + (vStep * 2));
                selectNextPotentialCell(cell);
            }
        }
    }

    void selectNextPotentialCell(final Cell cell) {
        if (cell != null && spotValue.getNextSpots().contains(getCoordinatesSpot(cell))) {
            selectNextPotentialCells(cell.getCoordinates());
        }
    }

    CoordinatesSpot getCoordinatesSpot(final Cell cell) {
        return CoordinatesSpot.valueOf(from, cell.getCoordinates());
    }
}
