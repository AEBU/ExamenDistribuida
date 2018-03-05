package uce.edu.bautista.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import uce.edu.bautista.model.Instrument;
import uce.edu.bautista.util.Constantes;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Alexis on 04/03/2018.
 */
@Controller
public class InstrumentController {
    RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        restTemplate = new RestTemplate();
    }

    @GetMapping("/instrument/create")
    public String instrumentForm(Model model){
        model.addAttribute("instrument", new Instrument());
        return "/instrument/form";
    }
    @PostMapping("/instrument/save")
    public String singerSave(@Valid Instrument instrument, BindingResult bindingResult, Model model) {
        restTemplate.postForEntity(Constantes.URL_INSTRUMENT+"create",instrument,Instrument.class);
        return "/home";
    }

    @GetMapping("/instrument/listar")
    public String singerList(Model model){
        List<Instrument> instruments = restTemplate.getForObject(Constantes.URL_INSTRUMENT + "listar", List.class);
        model.addAttribute("instruments", instruments);
        return "/instrument/list";
    }



}
