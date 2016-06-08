package ua.coral.corners.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.coral.corners.pojo.*;
import ua.coral.corners.util.DescMapUtil;

import java.util.Comparator;
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

//    private Map<Coordinates, Cell> getCells() {
//        return getCells(false);
//    }
//
//    private Map<Coordinates, Cell> getCells(boolean hasNewCopy) {
//        if (cellMap == null || hasNewCopy) {
//            cellMap = new HashMap<>(descService.getDesc().getCells());
//        }
//        return cellMap;
//    }

    public void handle() {
        StepSpotValue spotValue = new StepSpotValue();
        spotValue.setNextSpots(new StepSpotValueList());
        cellMap = descService.getDesc().getCells();

        cellMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isWhite())
                .forEach(entry -> chipStepService.selectPotentialCells(entry.getKey(), cellMap, spotValue));

//        printResult(spotValue.getNextSpots().getMaxValue());

        spotValue.getNextSpots().getStepSpotValues()
                .stream()
                .forEach(value -> {
                    StepSpotValueList newList = new StepSpotValueList();
                    value.setNextSpots(newList);
                    cellMap.entrySet()
                            .stream()
                            .filter(entry -> entry.getValue().isBlack())
                            .forEach(entry -> chipStepService.selectPotentialCells(entry.getKey(), cellMap, value));
                });

//        printResult(spotValue.getNextSpots().getChildMaxValue());

        spotValue.getNextSpots().getStepSpotValues()
                .stream()
                .forEach(o -> o.getNextSpots().getStepSpotValues()
                        .stream()
                        .forEach(value -> {
                            StepSpotValueList newList = new StepSpotValueList();
                            value.setNextSpots(newList);
                            cellMap.entrySet()
                                    .stream()
                                    .filter(entry -> entry.getValue().isWhite())
                                    .forEach(entry -> chipStepService.selectPotentialCells(entry.getKey(), cellMap, value));
                        }));

//        printResult(spotValue.getNextSpots().getChildMaxValue());

        spotValue.getNextSpots().getStepSpotValues()
                .stream()
                .forEach(o -> o.getNextSpots().getStepSpotValues()
                        .stream()
                        .forEach(subObj -> subObj.getNextSpots().getStepSpotValues()
                                .stream()
                                .forEach(value -> {
                                    StepSpotValueList newList = new StepSpotValueList();
                                    value.setNextSpots(newList);
                                    cellMap.entrySet()
                                            .stream()
                                            .filter(entry -> entry.getValue().isBlack())
                                            .forEach(entry -> chipStepService.selectPotentialCells(entry.getKey(), cellMap, value));
                                })));

//        printResult(spotValue.getNextSpots().getChildMaxValue());

        spotValue.getNextSpots().getStepSpotValues()
                .stream()
                .forEach(o -> o.getNextSpots().getStepSpotValues()
                        .stream()
                        .forEach(subObj -> subObj.getNextSpots().getStepSpotValues()
                                .stream()
                                .forEach(subSubObj -> subSubObj.getNextSpots().getStepSpotValues()
                                        .stream()
                                        .forEach(value -> {
                                            StepSpotValueList newList = new StepSpotValueList();
                                            value.setNextSpots(newList);
                                            cellMap.entrySet()
                                                    .stream()
                                                    .filter(entry -> entry.getValue().isWhite())
                                                    .forEach(entry -> chipStepService.selectPotentialCells(entry.getKey(), cellMap, value));
                                        }))));

//        printResult(spotValue.getNextSpots().getChildMaxValue());

        StepSpotValue rootChildMaxValue = spotValue.getNextSpots().getRootChildMaxValue();
        CoordinatesSpot spot = rootChildMaxValue.getSpot();
        moveService.move(descService.getDesc().getCells(), spot.getFrom(), spot.getTo());
    }

    private void printResult(StepSpotValue childMaxValue) {
        if (childMaxValue == null || cellMap == null) {
            return;
        }
        Map<Coordinates, Cell> cells = cellMap;
        LOG.info(DescMapUtil.toStringAsChips(cells));
//        LOG.info(DescMapUtil.toStringAsValue(cells));
        printResult(childMaxValue.getPreviousValue());
    }
}
