package uce.edu.bautista.services;

import uce.edu.bautista.model.Instrument;

import java.util.List;

/**
 * Created by Alexis on 02/03/2018.
 */
public interface InstrumentService {
    Instrument saveInstrument(Instrument singer);
    List<Instrument> listInstrument();
    Instrument getInstrument(Integer id);
}
