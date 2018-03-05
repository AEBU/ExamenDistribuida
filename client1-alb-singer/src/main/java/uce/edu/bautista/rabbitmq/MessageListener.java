package uce.edu.bautista.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uce.edu.bautista.Client1AlbSingerApplication;
import uce.edu.bautista.service.SingerService;

/**
 * Created by Alexis on 02/03/2018.
 */
@Service
public class MessageListener {
    private static final Logger log = LoggerFactory.getLogger(MessageListener.class);


    @Autowired
    SingerService singerService;


    @RabbitListener(queues = Client1AlbSingerApplication.QUEUE_SPECIFIC_NAME)
    public void receiveMessagex(final Message customMessage) {
        log.info("Recibiendo mensaje Como clase especifica: {}", customMessage.toString());
    }

    @RabbitListener(queues = Client1AlbSingerApplication.QUEUE_GENERIC_NAME)
    public void receiveMessage(final uce.edu.bautista.rabbitmq.Message message) {
        log.info("Recibiendo como mensaje generico: {}", message.toString());
        singerService.sendMail();
    }


}
