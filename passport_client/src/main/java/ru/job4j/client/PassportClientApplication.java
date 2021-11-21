package ru.job4j.client;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PassportClientApplication {
    @Bean
    public RestTemplate getTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(PassportClientApplication.class, args);
    }

    @KafkaListener(topics = {"passport-exp"})
    public void listenStatistic(ConsumerRecord<Integer, String> input) {
        System.out.printf("\n%s%n", input.value());
    }

}
