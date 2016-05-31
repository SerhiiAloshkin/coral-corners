package ua.coral.corners.service;

import ua.coral.corners.pojo.Chip;
import ua.coral.corners.pojo.ChipType;

import org.springframework.stereotype.Service;

import static ua.coral.corners.pojo.ChipType.BLACK;
import static ua.coral.corners.pojo.ChipType.EMPTY;
import static ua.coral.corners.pojo.ChipType.WHITE;

@Service
public class ChipService {

    public Chip getChip(final int hIndex, final int vIndex) {
        return new Chip(createChipByIndex(hIndex, vIndex));
    }

    private ChipType createChipByIndex(final int hIndex, final int vIndex) {
        if (hIndex < 3 && vIndex < 3) {
            return WHITE;
        } else if (hIndex > 4 && vIndex > 4) {
            return BLACK;
        }
        return EMPTY;
    }
}
