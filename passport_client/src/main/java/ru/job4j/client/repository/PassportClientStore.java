package ru.job4j.client.repository;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import ru.job4j.client.domain.Passport;

import java.util.Collections;
import java.util.List;

@Repository
public class PassportClientStore {
    private final String url = "http://localhost:8080/passports";
    private final RestTemplate client;

    public PassportClientStore(RestTemplate client) {
        this.client = client;
    }

    public Passport save(Passport passport) {
        return client
                .postForEntity((String.format("%s/", url)), passport, Passport.class)
                .getBody();
    }

    public List<Passport> findAll() {
        return getList(String.format("%s/", url));
    }

    public Passport update(int id, Passport passport) {
        return client
                .exchange(
                        String.format("%s/%s", url, id), HttpMethod.PUT,
                        new HttpEntity<>(passport), Passport.class).getBody();
    }

    public void delete(int id) {
        client.delete(String.format("%s/%s", url, id));
    }

    public List<Passport> findBySerial(int serial) {
        return getList(String.format(
                "%s/serial/%s", url, serial
        ));
    }

    public Passport findById(int id) {
        return client
                .getForEntity(String.format("%s/%s", url, id), Passport.class)
                .getBody();
    }

    public List<Passport> findExpirePassports() {
        return getList(String.format(
                "%s/unavailable", url));
    }

    public List<Passport> findReplaceable() {
        return getList(String.format(
                "%s/replaceable", url));
    }

    private List<Passport> getList(String url) {
        List<Passport> body = client.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Passport>>() {
                }
        ).getBody();
        return body == null ? Collections.emptyList() : body;
    }
}
