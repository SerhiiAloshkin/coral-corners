package ua.coral.corners.service

import spock.lang.Specification
import spock.lang.Unroll
import ua.coral.corners.pojo.Coordinates

class ChipStepServiceTest extends Specification {

    def service = new ChipStepService()
    def cellMap

    def setup() {
        def coordinatesService = new CoordinatesService()
        def cellValueService = new CellValueService()
        def chipService = new ChipService()
        def cellService = new CellService()
        cellService.coordinatesService = coordinatesService
        cellService.cellValueService = cellValueService
        cellService.chipService = chipService
        def descService = new DescService()
        descService.coordinatesService = coordinatesService
        descService.cellService = cellService
        def moveService = new MoveService()
        def valueService = new ValueService()
        cellMap = descService.getDesc().getCells()
        service.moveService = moveService
        service.valueService = valueService
        service.cells = new HashMap<>(cellMap)
    }

    @Unroll("hIndex = #hIndex | vIndex = #vIndex | mapSize = #mapSize | fromHIndex = #fromHIndex | fromVIndex = #fromVIndex")
    def 'first test'() {
        given:
        service.from = Coordinates.valueOf(fromHIndex, fromVIndex)

        expect:
        service.selectPotentialCell(hIndex, vIndex)
        mapSize == service.imageValueMap.size()
        service.cells == new HashMap(cellMap)

        where:
        hIndex  | vIndex | mapSize | fromHIndex | fromVIndex
        0       | 0      | 0       | 0          | 0
        1       | 1      | 0       | 0          | 0
        2       | 2      | 0       | 0          | 0
        3       | 3      | 1       | 0          | 0
        3       | 3      | 0       | 4          | 4
    }

    @Unroll
    def 'second test'() {
        setup:
        def spyService = Spy(ChipStepService)
        def coordinates = Coordinates.valueOf(0, 0)

        when:
        spyService.selectNextPotentialCells(coordinates)

        then:
        1 * spyService.selectNextPotentialCell(coordinates, -1, 0)
        1 * spyService.selectNextPotentialCell(coordinates, 1, 0)
        1 * spyService.selectNextPotentialCell(coordinates, 0, -1)
        1 * spyService.selectNextPotentialCell(coordinates, 0, 1)
    }
}