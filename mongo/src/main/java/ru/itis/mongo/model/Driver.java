package ru.itis.mongo.model;

import com.querydsl.core.annotations.QueryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import java.util.List;


@Entity
@Document(collection = "drivers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Driver {
    @Id
    private String _id;
    private String name;
    private Double cash;
    @DBRef
    private List<Car> cars;

}
