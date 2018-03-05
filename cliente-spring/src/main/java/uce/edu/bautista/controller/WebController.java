package uce.edu.bautista.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Alexis on 04/03/2018.
 */
@Controller
public class WebController {

    @GetMapping(value = "/")
    public String index() {
        return "/home";
    }
}
