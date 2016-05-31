package ua.coral.corners.pojo;

public class CellValue {
    private final int blackValue;
    private final int whiteValue;

    public CellValue(final int blackValue, final int whiteValue) {
        this.blackValue = blackValue;
        this.whiteValue = whiteValue;
    }

    public int getValue(final ChipType chipType) {
        switch (chipType) {
            case BLACK:
                return blackValue;
            case WHITE:
                return whiteValue;
        }
        return -1;
    }
}
