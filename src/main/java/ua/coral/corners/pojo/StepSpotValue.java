package ua.coral.corners.pojo;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.stream.Collectors;

public class StepSpotValue {

    private static final Logger LOG = Logger.getLogger(StepSpotValue.class);
    private Map<Coordinates, Cell> cells;
    private CoordinatesSpot spot;
    private Integer value;
    private StepSpotValueList nextSpots;
    private StepSpotValue previousValue;

    public Map<Coordinates, Cell> getCells() {
        return cells;
    }

    public void setCells(Map<Coordinates, Cell> cells) {
        this.cells = cells.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> {
                    try {
                        return (Cell) e.getValue().clone();
                    } catch (CloneNotSupportedException ex) {
                        LOG.error("Can't clone object", ex);
                        return e.getValue();
                    }
                }));
    }

    public CoordinatesSpot getSpot() {
        return spot;
    }

    public void setSpot(CoordinatesSpot spot) {
        this.spot = spot;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public StepSpotValueList getNextSpots() {
        return nextSpots;
    }

    public void setNextSpots(StepSpotValueList nextSpots) {
        this.nextSpots = nextSpots;
    }

    public StepSpotValue getPreviousValue() {
        return previousValue;
    }

    public void setPreviousValue(StepSpotValue previousValue) {
        this.previousValue = previousValue;
    }

    public StepSpotValue getRootValue() {
        if (previousValue.getPreviousValue() != null) {
            return getRootValue(previousValue.getPreviousValue());
        }
        return previousValue;
    }

    private StepSpotValue getRootValue(StepSpotValue childSpotValue) {
        if (childSpotValue.getPreviousValue() != null) {
            return getRootValue(childSpotValue.getPreviousValue());
        }
        return childSpotValue;
    }
}
