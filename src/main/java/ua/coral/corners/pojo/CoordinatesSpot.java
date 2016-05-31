package ua.coral.corners.pojo;

import java.util.Objects;

public class CoordinatesSpot {

    private Coordinates from;
    private Coordinates to;

    public CoordinatesSpot(Coordinates from, Coordinates to) {
        this.from = from;
        this.to = to;
    }

    public Coordinates getFrom() {
        return from;
    }

    public Coordinates getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoordinatesSpot that = (CoordinatesSpot) o;
        return Objects.equals(from, that.from) &&
                Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
