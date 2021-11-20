package ru.job4j.client.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int serial;
    private int number;
    @Temporal(TemporalType.DATE)
    private Date expDate;

    public Passport(int id, int serial, int number, Date expDate) {
        this.id = id;
        this.serial = serial;
        this.number = number;
        this.expDate = expDate;
    }

    public Passport() {
    }

    public int getId() {
        return id;
    }

    public int getSerial() {
        return serial;
    }

    public int getNumber() {
        return number;
    }

    public Date getExpDate() {
        return expDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Passport passport = (Passport) o;
        return id == passport.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Passport{"
                + "id=" + id
                + ", serial=" + serial
                + ", number=" + number
                + ", expDate=" + expDate
                + '}';
    }
}
