package ua.coral.corners.pojo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CoordinatesSpot {

    private Coordinates from;
    private Coordinates to;

    private CoordinatesSpot(Coordinates from, Coordinates to) {
        this.from = from;
        this.to = to;
    }

    public Coordinates getFrom() {
        return from;
    }

    public Coordinates getTo() {
        return to;
    }

    public static CoordinatesSpot valueOf(Coordinates from, Coordinates to) {
        return CoordinatesSpotCache.get(from, to);
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

    private static class CoordinatesSpotCache {

        private static final Map<Integer, CoordinatesSpot> map = new HashMap<>();

        private CoordinatesSpotCache() {
            super();
        }

        private static CoordinatesSpot get(Coordinates from, Coordinates to) {
            int hash = Objects.hash(from, to);
            if (!map.containsKey(hash)) {
                map.put(hash, new CoordinatesSpot(from, to));
            }
            return map.get(hash);
        }
    }
}
