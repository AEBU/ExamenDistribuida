package uce.edu.bautista.model;

import java.io.Serializable;

/**
 * Created by Alexis on 02/03/2018.
 */
public class Instrument implements Serializable {
    private Integer id;

    private String instrumentName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }
}
