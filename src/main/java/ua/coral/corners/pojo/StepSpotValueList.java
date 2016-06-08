package ua.coral.corners.pojo;

import java.util.LinkedList;
import java.util.List;
import java.util.OptionalInt;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class StepSpotValueList {

    private final List<StepSpotValue> stepSpotValues = new LinkedList<>();

    public void putIfAbsent(final StepSpotValue value) {
        final CoordinatesSpot spot = value.getSpot();
        if (stepSpotValues.stream().noneMatch(v -> v.getSpot().equals(spot))) {
            stepSpotValues.add(value);
        }
    }

    public boolean contains(final CoordinatesSpot value) {
        return stepSpotValues.stream().parallel().anyMatch(o -> o.getSpot().equals(value));
    }

    public int size() {
        return stepSpotValues.size();
    }

    public StepSpotValue getMaxValue() {
        OptionalInt max = stepSpotValues.stream().mapToInt(o -> o.getValue()).max();
        if (!max.isPresent()) {
            return null;
        }
        List<StepSpotValue> collect = stepSpotValues.stream()
                .filter(o -> o.getValue() == max.getAsInt())
                .collect(Collectors.toList());
        return collect.get(ThreadLocalRandom.current().nextInt(collect.size()));
    }

    public StepSpotValue getChildMaxValue() {
        List<StepSpotValue> maxValues = new LinkedList<>();
        for (StepSpotValue value : stepSpotValues) {
            if (value.getNextSpots() != null) {
                maxValues.add(value.getNextSpots().getChildMaxValue());
            } else {
                return getMaxValue();
            }
        }

        StepSpotValue maxSpotValue = maxValues.stream()
                .max((o1, o2) -> o1.getValue().compareTo(o2.getValue())).get();

        return maxSpotValue;
    }

    public StepSpotValue getRootChildMaxValue() {
        return getChildMaxValue().getRootValue();
    }

    public List<StepSpotValue> getStepSpotValues() {
        return stepSpotValues;
    }
}
