package uce.edu.bautista.service;

import uce.edu.bautista.model.Singer;

import java.util.List;

/**
 * Created by Alexis on 02/03/2018.
 */
public interface SingerService {
    Singer saveSinger(Singer singer);
    List<Singer> listSinger();
    Singer getSinger(Integer id);
    void sendMail();
    List<String> getEmails();
}
