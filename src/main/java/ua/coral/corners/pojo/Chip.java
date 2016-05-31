package ua.coral.corners.pojo;

public class Chip {

    private ChipType chipType;

    public Chip(final ChipType chipType) {
        this.chipType = chipType;
    }

    public ChipType getChipType() {
        return chipType;
    }

    public void setChipType(final ChipType chipType) {
        this.chipType = chipType;
    }

    @Override
    public String toString() {
        return "Chip{" +
                "chipType=" + chipType +
                '}';
    }
}
