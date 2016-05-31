package ua.coral.corners.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.coral.corners.pojo.Cell;
import ua.coral.corners.pojo.Coordinates;
import ua.coral.corners.pojo.CoordinatesSpot;
import ua.coral.corners.pojo.StepSpotValueList;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Service
public class CellHandlerService {

    private static final Logger LOG = Logger.getLogger(CellHandlerService.class);
    @Autowired
    private DescService descService;
    @Autowired
    private ChipStepService chipStepService;
    @Autowired
    private MoveService moveService;
    private Map<Coordinates, Cell> cellMap;
    private static Comparator<? super Map.Entry<CoordinatesSpot, Integer>> comparator =
            (entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue());

    private Map<Coordinates, Cell> getCells() {
        return getCells(false);
    }

    private Map<Coordinates, Cell> getCells(boolean hasNewCopy) {
        if (cellMap == null || hasNewCopy) {
            cellMap = new HashMap<>(descService.getDesc().getCells());
        }
        return cellMap;
    }

    public void handle() {
        StepSpotValueList list = new StepSpotValueList();
        cellMap = new HashMap<>(descService.getDesc().getCells());

        cellMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isWhite())
                .forEach(entry -> chipStepService.selectPotentialCells(entry.getKey(), cellMap, list));

        list.getStepSpotValues()
                .stream()
                .forEach(value -> {
                    StepSpotValueList newList = new StepSpotValueList();
                    value.setNextSpots(newList);
                    value.getCells().entrySet()
                        .stream()
                        .filter(entry -> entry.getValue().isBlack())
                        .forEach(entry -> {
                            chipStepService.selectPotentialCells(entry.getKey(), value.getCells(), newList);
                        });
                });

        list.getStepSpotValues()
                .stream()
                .forEach(o -> o.getNextSpots().getStepSpotValues()
                        .stream()
                        .forEach(value -> {
                            StepSpotValueList newList = new StepSpotValueList();
                            value.setNextSpots(newList);
                            value.getCells().entrySet()
                                    .stream()
                                    .filter(entry -> entry.getValue().isWhite())
                                    .forEach(entry -> {
                                        chipStepService.selectPotentialCells(entry.getKey(), value.getCells(), newList);
                                    });
                        }));

        CoordinatesSpot spot = list.getChildMaxValue().getSpot();
        moveService.move(descService.getDesc().getCells(), spot.getFrom(), spot.getTo());
    }
}
