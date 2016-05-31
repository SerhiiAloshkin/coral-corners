package ua.coral.corners.controller;

import org.springframework.ui.Model;
import ua.coral.corners.service.CellHandlerService;
import ua.coral.corners.service.DescService;
import ua.coral.corners.service.MoveService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.coral.corners.service.StepService;

@Controller
public class HomeController {

    @Autowired
    private DescService descService;
    @Autowired
    private MoveService moveService;
    @Autowired
    private CellHandlerService cellHandlerService;
    @Autowired
    private StepService stepService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String step(Model model) {
        if (stepService.isStep4Black()) {
            cellHandlerService.handle();
            stepService.step4White();
        }
        model.addAttribute("cells", descService.getDesc().getCells());
        model.addAttribute("blackValue", descService.getBlackValue());
        model.addAttribute("whiteValue", descService.getWhiteValue());
        return "index1";
    }

    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public void step(String hIndex, String vIndex) {
        moveService.setSelectedCell(hIndex, vIndex);
    }
}
