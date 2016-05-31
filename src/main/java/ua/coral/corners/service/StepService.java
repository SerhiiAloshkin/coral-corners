package ua.coral.corners.service;

import org.springframework.stereotype.Service;

@Service
public class StepService {

    private boolean isStep4White = false;
    private boolean isStep4Black = true;

    public void step4White() {
        if (isStep4Black) {
            isStep4Black = false;
            isStep4White = true;
        }
    }

    public void step4Black() {
        if (isStep4White) {
            isStep4White = false;
            isStep4Black = true;
        }
    }

    public boolean isStep4White() {
        return isStep4White;
    }

    public boolean isStep4Black() {
        return isStep4Black;
    }
}
