package ru.itis.hm2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.hm2.model.PassportData;

@Repository
public interface PassportDataRepository extends JpaRepository<PassportData, Integer> {
}
