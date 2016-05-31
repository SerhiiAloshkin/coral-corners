package ua.coral.corners.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.coral.corners.pojo.Cell;
import ua.coral.corners.pojo.Chip;
import ua.coral.corners.pojo.ChipType;
import ua.coral.corners.pojo.Coordinates;

import java.util.Map;
import java.util.stream.Stream;

import static ua.coral.corners.pojo.ChipType.*;

@Service
public class MoveService {

    @Autowired
    private DescService descService;
    @Autowired
    private StepService stepService;
    private ChipType selectedType = EMPTY;

    public void move(final Map<Coordinates, Cell> cells, final Coordinates from, final Coordinates to) {
        final Chip chip = cells.get(from).getChip();
        cells.get(from).setChip(cells.get(to).getChip());
        cells.get(to).setChip(chip);
    }

    public void setSelectedCell(final String hIndex, final String vIndex) {
        final Coordinates coordinates = Coordinates.valueOf(hIndex, vIndex);
        final Cell cell = descService.getDesc().getCells().get(coordinates);
        final Cell selectedCell = getSelectedCell();
        if (cell.isGreen() && selectedCell != null) {
            move(descService.getDesc().getCells(), selectedCell.getCoordinates(), coordinates);
            stepService.step4Black();
            cleanGreenCells();
            return;
        }
        cleanGreenCells();
        if (!cell.isEmpty()) {
            selectedType = cell.getChip().getChipType();
            cell.setSelected(true);
            cell.getChip().setChipType(BLUE);
            selectPotentialCells(coordinates);
        }
    }

    private void selectPotentialCells(final Coordinates coordinates) {
        selectPotentialCell(coordinates, -1, 0);
        selectPotentialCell(coordinates, 1, 0);
        selectPotentialCell(coordinates, 0, -1);
        selectPotentialCell(coordinates, 0, 1);
    }

    private void selectPotentialCell(final Coordinates coordinates, final int hStep, final int vStep) {
        Cell cell = selectPotentialCell(coordinates.getHIndex() + hStep, coordinates.getVIndex() + vStep);
        if (cell != null && !cell.isGreen()) {
            cell = selectPotentialCell(coordinates.getHIndex() + (hStep * 2), coordinates.getVIndex() + (vStep * 2));
            selectNextPotentialCell(cell);
        }
    }

    private Cell selectPotentialCell(final int hIndex, final int vIndex) {
        final Coordinates coord = Coordinates.valueOf(hIndex, vIndex);
        final Cell cell = descService.getDesc().getCells().get(coord);
        if (cell != null && cell.isEmpty()) {
            cell.getChip().setChipType(GREEN);
        }
        return cell;
    }

    private void selectNextPotentialCells(final Coordinates coordinates) {
        selectNextPotentialCell(coordinates, -1, 0);
        selectNextPotentialCell(coordinates, 1, 0);
        selectNextPotentialCell(coordinates, 0, -1);
        selectNextPotentialCell(coordinates, 0, 1);
    }

    private void selectNextPotentialCell(final Coordinates coordinates, final int hStep, final int vStep) {
        Cell cell = descService.getDesc().getCells().get(Coordinates.valueOf(coordinates.getHIndex() + hStep, coordinates.getVIndex() + vStep));
        if (cell != null && (cell.isBlack() || cell.isWhite())) {
            cell = descService.getDesc().getCells().get(Coordinates.valueOf(coordinates.getHIndex() + (hStep * 2), coordinates.getVIndex() + (vStep * 2)));
            if (cell != null && !cell.isGreen()) {
                cell = selectPotentialCell(coordinates.getHIndex() + (hStep * 2), coordinates.getVIndex() + (vStep * 2));
                selectNextPotentialCell(cell);
            }
        }
    }

    private void selectNextPotentialCell(final Cell cell) {
        if (cell != null && cell.isGreen()) {
            selectNextPotentialCells(cell.getCoordinates());
        }
    }

    private void cleanGreenCells() {
        Stream<Map.Entry<Coordinates, Cell>> stream = descService.getDesc().getCells().entrySet().stream();
        stream.filter(map -> map.getValue().isGreen())
                .forEach(e -> e.getValue().getChip().setChipType(EMPTY) );
    }

    private Cell getSelectedCell() {
        Stream<Map.Entry<Coordinates, Cell>> stream = descService.getDesc().getCells().entrySet().stream();
        return stream
                .filter(map -> map.getValue().isSelected())
                .map(c -> {
                    c.getValue().setSelected(false);
                    c.getValue().getChip().setChipType(selectedType);
                    return c.getValue();
                })
                .findFirst()
                .orElse(null);
    }
}
