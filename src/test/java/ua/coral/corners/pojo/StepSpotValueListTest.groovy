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
        def list = new StepSpotValueList()
        def spot1 = new CoordinatesSpot(Coordinates.valueOf(0,0), Coordinates.valueOf(0, 1))
        def spot2 = new CoordinatesSpot(Coordinates.valueOf(0, 1), Coordinates.valueOf(1, 1))
        def spot3 = new CoordinatesSpot(Coordinates.valueOf(0, 2), Coordinates.valueOf(2, 1))
        def spot4 = new CoordinatesSpot(Coordinates.valueOf(0, 3), Coordinates.valueOf(3, 1))

        def child2StepSpot1 = new StepSpotValue(spot: spot1, value: 1000)
        def child2StepSpot2 = new StepSpotValue(spot: spot2, value: 900)
        def child2StepSpot3 = new StepSpotValue(spot: spot3, value: 800)
        def child2StepSpot4 = new StepSpotValue(spot: spot4, value: 700)

        def child2SpotList1 = new StepSpotValueList()
        def child2SpotList2 = new StepSpotValueList()
        def child2SpotList3 = new StepSpotValueList()
        def child2SpotList4 = new StepSpotValueList()

        child2SpotList1.putIfAbsent(child2StepSpot1)
        child2SpotList1.putIfAbsent(child2StepSpot2)
        child2SpotList2.putIfAbsent(child2StepSpot2)
        child2SpotList2.putIfAbsent(child2StepSpot4)
        child2SpotList3.putIfAbsent(child2StepSpot2)
        child2SpotList3.putIfAbsent(child2StepSpot3)
        child2SpotList4.putIfAbsent(child2StepSpot2)
        child2SpotList4.putIfAbsent(child2StepSpot4)

        def childStepSpot1 = new StepSpotValue(spot: spot1, value: 300, nextSpots: child2SpotList1)
        def childStepSpot2 = new StepSpotValue(spot: spot2, value: 400, nextSpots: child2SpotList2)
        def childStepSpot3 = new StepSpotValue(spot: spot3, value: 600, nextSpots: child2SpotList3)
        def childStepSpot4 = new StepSpotValue(spot: spot4, value: 500, nextSpots: child2SpotList4)

        def childSpotList1 = new StepSpotValueList()
        def childSpotList2 = new StepSpotValueList()
        def childSpotList3 = new StepSpotValueList()
        def childSpotList4 = new StepSpotValueList()

        childSpotList1.putIfAbsent(childStepSpot1)
        childSpotList1.putIfAbsent(childStepSpot2)
        childSpotList2.putIfAbsent(childStepSpot2)
        childSpotList2.putIfAbsent(childStepSpot4)
        childSpotList3.putIfAbsent(childStepSpot2)
        childSpotList3.putIfAbsent(childStepSpot3)
        childSpotList4.putIfAbsent(childStepSpot2)
        childSpotList4.putIfAbsent(childStepSpot4)

        list.putIfAbsent(new StepSpotValue(spot: spot1, value: 100, nextSpots: childSpotList1))
        list.putIfAbsent(new StepSpotValue(spot: spot2, value: 200, nextSpots: childSpotList2))
        list.putIfAbsent(new StepSpotValue(spot: spot3, value: 100, nextSpots: childSpotList3))
        list.putIfAbsent(new StepSpotValue(spot: spot4, value: 200, nextSpots: childSpotList4))

        when:
        def actual = list.getChildMaxValue()

        then:
        actual.value == 100
        actual.spot == spot1
        actual.maxValue == 1000
    }
}
