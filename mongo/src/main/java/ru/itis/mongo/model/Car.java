package ru.itis.mongo.model;

import com.mysema.query.annotations.QueryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Document(collection = "cars")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car {
    @Id
    private String _id;
    private String colour;
    private List<String> features;
    private Integer maxSpeed;
}
