package uce.edu.bautista.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uce.edu.bautista.model.Singer;
import uce.edu.bautista.repository.SingerRepository;

import java.util.List;

/**
 * Created by Alexis on 02/03/2018.
 */
@Service
public class SingerServiceImpl implements SingerService {
    @Autowired
    private SingerRepository singerRepository;
    @Autowired
    private SendMailService sendMailService;

    @Override
    public Singer saveSinger(Singer singer) {
        return singerRepository.save(singer);
    }

    @Override
    public List<Singer> listSinger() {
        return singerRepository.findAll();
    }

    @Override
    public Singer getSinger(Integer id) {
        return singerRepository.getOne(id);
    }

    @Override
    public void sendMail(){
        List<String> emails = getEmails();
        for (String email : emails) {
            sendMailService.sendMail(email);
        }
    }

    @Override
    public List<String> getEmails(){
        return singerRepository.listEmails();
    }
}
