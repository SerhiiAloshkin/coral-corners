package ua.coral.corners.service;

import ua.coral.corners.pojo.Coordinates;

import org.springframework.stereotype.Service;

@Service
public class CoordinatesService {

    public Coordinates getCoordinates(final int hIndex, final int vIndex) {
        return Coordinates.valueOf(hIndex, vIndex);
    }
}
