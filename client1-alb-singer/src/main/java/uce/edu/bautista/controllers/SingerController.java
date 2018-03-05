package uce.edu.bautista.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import uce.edu.bautista.model.Singer;
import uce.edu.bautista.service.SingerService;

import java.util.List;

/**
 * Created by Alexis on 04/03/2018.
 */
@RestController
public class SingerController {
    @Autowired
    SingerService singerService;

    public static final Logger logger = LoggerFactory.getLogger(SingerController.class);


    @RequestMapping(value = "/singer/create",method = RequestMethod.POST)
    public @ResponseBody
    Singer createSinger(@RequestBody Singer singer, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Singer : {}", singer.getEmail());
        Singer instrumentSave=singerService.saveSinger(singer);
        return instrumentSave;
    }



    @RequestMapping(value = "/singer/listar",method = RequestMethod.GET)
    public List<Singer> listInstrument(){
        return singerService.listSinger();
    }


}
