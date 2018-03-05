package uce.edu.bautista.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uce.edu.bautista.model.Instrument;

/**
 * Created by Alexis on 02/03/2018.
 */
@Repository
public interface InstrumentRepository extends JpaRepository<Instrument,Integer> {
}
