package uce.edu.bautista;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

@SpringBootApplication
@EnableDiscoveryClient
public class Client1AlbSingerApplication {
	public static final String QUEUE_GENERIC_NAME = "appGenericQueue";
	public static final String QUEUE_SPECIFIC_NAME = "appSpecificQueue";

	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	@Bean
	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
		return new MappingJackson2MessageConverter();
	}	public static void main(String[] args) {
		SpringApplication.run(Client1AlbSingerApplication.class, args);
	}
}
