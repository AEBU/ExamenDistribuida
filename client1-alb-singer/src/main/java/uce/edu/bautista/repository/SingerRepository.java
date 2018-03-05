package uce.edu.bautista.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uce.edu.bautista.model.Singer;

import java.util.List;

/**
 * Created by Alexis on 02/03/2018.
 */
@Repository
public interface SingerRepository extends JpaRepository<Singer,Integer> {
//    Listamos los mails de todos los usaurios
    @Query(value = "select s.email from singer s",nativeQuery = true)
    List<String> listEmails();

}
