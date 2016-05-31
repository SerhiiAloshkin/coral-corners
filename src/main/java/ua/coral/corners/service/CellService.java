package ua.coral.corners.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.coral.corners.pojo.Cell;

@Service
public class CellService {

    @Autowired
    private CoordinatesService coordinatesService;
    @Autowired
    private CellValueService cellValueService;
    @Autowired
    private ChipService chipService;

    public Cell getCell(final int hIndex, final int vIndex) {
        final Cell cell = Cell.valueOf(coordinatesService.getCoordinates(hIndex, vIndex));
        cell.setCellValue(cellValueService.getCellValue(hIndex, vIndex));
        cell.setChip(chipService.getChip(hIndex, vIndex));
        return cell;
    }
}
