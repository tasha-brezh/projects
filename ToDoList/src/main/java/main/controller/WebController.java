package main.controller;

import main.model.Point;
import main.model.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
public class WebController {

    @RequestMapping("/")
    private String home(){
        return("index");
    }
}
