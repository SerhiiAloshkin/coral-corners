package ua.coral.corners.pojo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static ua.coral.corners.engine.Constants.MAX_LENGTH;

public class Coordinates {

    private final int hIndex;
    private final int vIndex;

    private Coordinates(final int hIndex, final int vIndex) {
        this.hIndex = hIndex;
        this.vIndex = vIndex;
    }

    public int getHIndex() {
        return hIndex;
    }

    public int getVIndex() {
        return vIndex;
    }

    public static Coordinates valueOf(final String hIndex, final String vIndex) {
        return valueOf(Integer.valueOf(hIndex), Integer.valueOf(vIndex));
    }

    public static Coordinates valueOf(final int hIndex, final int vIndex) {
        return CoordinatesCache.map.get(Objects.hash(hIndex, vIndex));
    }

    public static boolean isAvailable(final int hIndex, final int vIndex) {
        return hIndex >= 0 && hIndex < MAX_LENGTH && vIndex >= 0 && vIndex < MAX_LENGTH;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Objects.equals(hIndex, that.hIndex) &&
                Objects.equals(vIndex, that.vIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hIndex, vIndex);
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "hIndex=" + hIndex +
                ", vIndex=" + vIndex +
                '}';
    }

    private static class CoordinatesCache {

        private static final Map<Integer, Coordinates> map = new HashMap<>(MAX_LENGTH * 2);

        static {
            for (int h = 0; h < MAX_LENGTH; h++) {
                for (int v = 0; v < MAX_LENGTH; v++) {
                    Coordinates coordinates = new Coordinates(h, v);
                    map.put(coordinates.hashCode(), coordinates);
                }
            }
        }

        private CoordinatesCache() {
            super();
        }
    }
}
