package uce.edu.bautista.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uce.edu.bautista.Client2InstrumApplication;

/**
 * Created by Alexis on 02/03/2018.
 */
@Service
public class CustomMessageSender {
    private static final Logger log = LoggerFactory.getLogger(CustomMessageSender.class);

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public CustomMessageSender(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage() {
        final Message message = new Message("Hello Aqui enviando un mail!");
        log.info("Sending mensaje...");
        rabbitTemplate.convertAndSend(Client2InstrumApplication.EXCHANGE_NAME, Client2InstrumApplication.ROUTING_KEY, message);
    }
}
