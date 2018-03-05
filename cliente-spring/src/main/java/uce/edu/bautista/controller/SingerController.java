package uce.edu.bautista.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import uce.edu.bautista.model.Singer;
import uce.edu.bautista.util.Constantes;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Alexis on 04/03/2018.
 */
@Controller
public class SingerController {
    RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        restTemplate = new RestTemplate();
    }

    @GetMapping("/singer/create")
    public String singerForm(Model model){
        model.addAttribute("singer", new Singer());
        return "/singer/form";
    }
    @PostMapping("/singer/save")
    public String singerSave(@Valid Singer singer, BindingResult bindingResult, Model model) {
        restTemplate.postForEntity(Constantes.URL_SINGER+"create",singer,Singer.class);
        return "/home";
    }

    @GetMapping("/singer/listar")
    public String singerList(Model model){
        List<Singer> singers = restTemplate.getForObject(Constantes.URL_SINGER + "listar", List.class);
        model.addAttribute("singers", singers);
        return "/singer/list";
    }

}
