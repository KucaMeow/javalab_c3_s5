package ru.itis.hm3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.hm3.model.Service;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {
    @Query("select s from Service s where size(s.wheels) > :count")
    List<Service> findAllBySizeOfWheels (@Param("count") int count);
}
