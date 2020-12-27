package ru.itis.hm2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.hm2.model.PersonalData;

import java.util.Optional;

@Repository
public interface PersonalDataRepository extends JpaRepository<PersonalData, Integer> {
    public Optional<PersonalData> findByPassCodeIs(String passCode);
}
