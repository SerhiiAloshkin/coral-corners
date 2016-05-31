package ua.coral.corners.service;

import org.springframework.stereotype.Service;
import ua.coral.corners.pojo.Cell;
import ua.coral.corners.pojo.Chip;
import ua.coral.corners.pojo.ChipType;
import ua.coral.corners.pojo.Coordinates;

import java.util.Map;

@Service
public class ValueService {

    public int getValue(final ChipType chipType, final Map<Coordinates, Cell> cells) {
        int value = 0;
        for (final Map.Entry<Coordinates, Cell> entry : cells.entrySet()) {
            final Cell cell = entry.getValue();
            final Chip chip = cell.getChip();
            if (chip.getChipType() == chipType) {
                value += cell.getCellValue().getValue(chipType);
            }
        }
        return value;
    }
}
