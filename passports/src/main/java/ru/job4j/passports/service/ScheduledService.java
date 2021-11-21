package ru.job4j.passports.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.job4j.passports.domain.Passport;
import ru.job4j.passports.repository.PassportRepo;

import java.util.List;

@EnableScheduling
@Service
public class ScheduledService {
    private final KafkaTemplate<Integer, String> template;
    private final PassportRepo repo;

    public ScheduledService(PassportRepo repo, KafkaTemplate<Integer, String> template) {
        this.repo = repo;
        this.template = template;
    }

    @Scheduled(fixedDelay = 10000)
    public void scheduleFixedDelayTask() {
        List<Passport> expPassports = repo.findPassportWithExpireDate();
        if (expPassports.size() != 0) {
            StringBuilder msg = new StringBuilder("Passport with serial and number: ");
            for (var passport : expPassports) {
                msg.append(passport.getSerial())
                        .append(" ")
                        .append(passport.getNumber())
                        .append(System.lineSeparator());
            }
            msg.append("expired and need to be change");
            template.send("passport", msg.toString());
        }
    }
}
