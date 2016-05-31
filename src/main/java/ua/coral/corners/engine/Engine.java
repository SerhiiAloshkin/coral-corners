package ua.coral.corners.engine;

import ua.coral.corners.pojo.Cell;
import ua.coral.corners.pojo.Coordinates;
import ua.coral.corners.service.DescService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Engine {

    @Autowired
    private DescService descService;

    public void step(final Coordinates coordinates) {
        final Cell cell = descService.getCell(coordinates);
        switch (cell.getChip().getChipType()) {
            case BLACK:
                stepForBlack(coordinates);
                break;
            case WHITE:
                stepForWhite(coordinates);
                break;
        }
    }

    private void stepForBlack(final Coordinates coordinates) {
        if (!canNextStep(coordinates)) {
            return;
        }

    }

    private void stepForWhite(final Coordinates coordinates) {
        if (!canNextStep(coordinates)) {
            return;
        }
    }

    private boolean canNextStep(final Coordinates coordinates) {
        boolean bResult = false;
        final int hIndex = coordinates.getHIndex();
        final int vIndex = coordinates.getVIndex();
        bResult |= isNotCellEmpty(hIndex + 1, vIndex);
        bResult |= isNotCellEmpty(hIndex + 2, vIndex);
        bResult |= isNotCellEmpty(hIndex, vIndex + 1);
        bResult |= isNotCellEmpty(hIndex, vIndex + 2);
        bResult |= isNotCellEmpty(hIndex - 1, vIndex);
        bResult |= isNotCellEmpty(hIndex - 2, vIndex);
        bResult |= isNotCellEmpty(hIndex, vIndex - 1);
        bResult |= isNotCellEmpty(hIndex, vIndex - 2);
        return bResult;
    }

    private boolean isNotCellEmpty(final int hIndex, final int vIndex) {
        return descService.isEmptyCell(Coordinates.valueOf(hIndex, vIndex));
    }
}
