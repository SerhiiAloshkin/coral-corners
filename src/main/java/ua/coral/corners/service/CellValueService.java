package ua.coral.corners.service;

import ua.coral.corners.pojo.CellValue;

import org.springframework.stereotype.Service;

import static ua.coral.corners.engine.Values.BLACK_VALUES;
import static ua.coral.corners.engine.Values.WHITE_VALUES;

@Service
public class CellValueService {

    public CellValue getCellValue(final int hIndex, final int vIndex) {
        return new CellValue(WHITE_VALUES[hIndex][vIndex], BLACK_VALUES[hIndex][vIndex]);
    }
}
