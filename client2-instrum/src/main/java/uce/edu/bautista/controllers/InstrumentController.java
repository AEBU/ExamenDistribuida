package uce.edu.bautista.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import uce.edu.bautista.model.Instrument;
import uce.edu.bautista.services.InstrumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * Created by Alexis on 02/03/2018.
 */
@RestController
public class InstrumentController {

    public static final Logger logger = LoggerFactory.getLogger(InstrumentController.class);

    @Autowired
    InstrumentService instrumentService;


    @RequestMapping(value = "/instrument/create",method = RequestMethod.POST)
    public @ResponseBody Instrument createUser(@RequestBody Instrument instrument, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Instrument : {}", instrument.getInstrumentName());
        Instrument instrumentSave=instrumentService.saveInstrument(instrument);
        return instrumentSave;
    }



    @RequestMapping(value = "/instrument/listar",method = RequestMethod.GET)
    public List<Instrument>  listInstrument(){
        return instrumentService.listInstrument();
    }

}
