package ru.job4j.passports.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.passports.domain.Passport;

import java.util.Date;
import java.util.List;

public interface PassportRepo extends CrudRepository<Passport, Integer> {
    @Query("select p from Passport p where p.serial = ?1")
    List<Passport> findPassportBySerial(int serial);

    @Query("select p from Passport p where p.expDate < CURRENT_DATE")
    List<Passport> findPassportWithExpireDate();

    @Query("select p from Passport p where p.expDate <= ?1 and p.expDate > CURRENT_DATE")
    List<Passport> findReplaceablePassports(Date date);
}
