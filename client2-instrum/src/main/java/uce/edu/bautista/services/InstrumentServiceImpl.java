package uce.edu.bautista.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uce.edu.bautista.model.Instrument;
import uce.edu.bautista.rabbitmq.CustomMessageSender;
import uce.edu.bautista.repository.InstrumentRepository;

import java.util.List;

/**
 * Created by Alexis on 02/03/2018.
 */
@Service
public class InstrumentServiceImpl implements InstrumentService {
    @Autowired
    InstrumentRepository instrumentRepository;

    @Autowired
    CustomMessageSender customMessageSender;
    @Override
    public Instrument saveInstrument(Instrument singer) {
        customMessageSender.sendMessage();
        return instrumentRepository.save(singer);
    }

    @Override
    public List<Instrument> listInstrument() {
        return instrumentRepository.findAll();
    }

    @Override
    public Instrument getInstrument(Integer id) {
        return instrumentRepository.getOne(id);
    }
}
