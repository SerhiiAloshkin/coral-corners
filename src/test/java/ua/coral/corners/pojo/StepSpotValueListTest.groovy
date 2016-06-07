package ua.coral.corners.pojo

import spock.lang.Specification

class StepSpotValueListTest extends Specification {

    def 'first test'() {
        given:
        def list = new StepSpotValueList()
        def spotValue1 = new StepSpotValue(spot: new CoordinatesSpot(Coordinates.valueOf(0,0), Coordinates.valueOf(0, 1)), value: 100)
        def spotValue2 = new StepSpotValue(spot: new CoordinatesSpot(Coordinates.valueOf(0,1), Coordinates.valueOf(1, 1)), value: 100)

        when:
        list.putIfAbsent(spotValue1)

        then:
        list.size() == 1

        when:
        list.putIfAbsent(spotValue2)

        then:
        list.size() == 2

        when:
        list.putIfAbsent(spotValue1)

        then:
        list.size() == 2
    }

    def 'second test'() {
        given:
        def list = new StepSpotValueList()
        def spot1 = new CoordinatesSpot(Coordinates.valueOf(0,0), Coordinates.valueOf(0, 1))
        def spot2 = new CoordinatesSpot(Coordinates.valueOf(0, 1), Coordinates.valueOf(1, 1))
        def spot3 = new CoordinatesSpot(Coordinates.valueOf(0, 2), Coordinates.valueOf(2, 1))
        def spot4 = new CoordinatesSpot(Coordinates.valueOf(0, 3), Coordinates.valueOf(3, 1))
        list.putIfAbsent(new StepSpotValue(spot: spot1, value: 100))
        list.putIfAbsent(new StepSpotValue(spot: spot2, value: 200))
        list.putIfAbsent(new StepSpotValue(spot: spot3, value: 100))
        list.putIfAbsent(new StepSpotValue(spot: spot4, value: 200))

        when:
        def actual = list.getMaxValue()

        then:
        actual.value == 200
        actual.spot == spot2 || actual.spot == spot4
    }

    def 'third test'() {
        given:
        def list = new StepSpotValueList()
        def spot1 = new CoordinatesSpot(Coordinates.valueOf(0,0), Coordinates.valueOf(0, 1))
        def spot2 = new CoordinatesSpot(Coordinates.valueOf(0, 1), Coordinates.valueOf(1, 1))
        def spot3 = new CoordinatesSpot(Coordinates.valueOf(0, 2), Coordinates.valueOf(2, 1))
        def spot4 = new CoordinatesSpot(Coordinates.valueOf(0, 3), Coordinates.valueOf(3, 1))
        list.putIfAbsent(new StepSpotValue(spot: spot1, value: 100))
        list.putIfAbsent(new StepSpotValue(spot: spot2, value: 200))
        list.putIfAbsent(new StepSpotValue(spot: spot3, value: 100))
        list.putIfAbsent(new StepSpotValue(spot: spot4, value: 200))

        when:
        def actual = list.getChildMaxValue()

        then:
        actual.value == 200
        actual.spot == spot2 || actual.spot == spot4
    }

    def 'fourth test'() {
        given:

        def svl = new StepSpotValueList()
        def svl1 = new StepSpotValueList()
        def svl2 = new StepSpotValueList()

        def spotValue = new StepSpotValue()
        spotValue.setNextSpots(svl)

        def svc = new StepSpotValue(
                spot: new CoordinatesSpot(Coordinates.valueOf(0,0), Coordinates.valueOf(1,1)),
                value: 300,
                nextSpots: svl1
        )

        svl.putIfAbsent(svc)

        def svc1 = new StepSpotValue(
                spot: new CoordinatesSpot(Coordinates.valueOf(1,1), Coordinates.valueOf(2,2)),
                value: 600,
                previousValue: svc,
                nextSpots: svl2
        )

        svl1.putIfAbsent(svc1)

        def svc2 = new StepSpotValue(
                spot: new CoordinatesSpot(Coordinates.valueOf(2,2), Coordinates.valueOf(3,3)),
                value: 1000,
                previousValue: svc1
        )

        svl2.putIfAbsent(svc2)

        when:
        def actual = spotValue.getNextSpots().getChildMaxValue()

        then:
        actual.getValue() == 1000
        actual.getRootValue().value == 300
    }
}
