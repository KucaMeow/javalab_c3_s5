package ru.itis.mongo.repository.noJpa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.*;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.springframework.stereotype.Repository;
import ru.itis.mongo.model.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

@Repository
public class CarCrudRepositoryDriverImpl implements CarCrudRepository {

    private MongoCollection<Document> collection;

    public CarCrudRepositoryDriverImpl() {
        MongoClient client = MongoClients.create();
        MongoDatabase database = client.getDatabase("Car&Driver");
        collection = database.getCollection("cars");
    }

    @Override
    public List<Car> findAll() {
        List<Car> cars = new ArrayList<>();
        collection.find().forEach(d -> {
            try {
                cars.add((new ObjectMapper()).readValue(d.toJson(), Car.class));
            } catch (JsonProcessingException e) {
                throw new IllegalStateException(e);
            }
        });
        return cars;
    }

    @Override
    public Optional<Car> find(String _id) {
        Car toReturn = null;

        Document searchQuery = new Document();
        searchQuery.append("_id", _id);

        FindIterable<Document> result = collection.find(searchQuery)
                .projection(fields(include("_id", "colour", "features", "maxSpeed")));
        for(Document doc : result) {
            try {
                toReturn = (new ObjectMapper()).readValue(doc.toJson(), Car.class);
            } catch (JsonProcessingException e) {
                throw new IllegalStateException(e);
            }
        }

        return Optional.ofNullable(toReturn);
    }

    @Override
    public Car save(Car car) {
        Document newD = null;
        try {
            newD = Document.parse((new ObjectMapper()).writeValueAsString(car));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
        InsertOneResult document =  collection.insertOne(newD);
        car.set_id(document.getInsertedId().toString());
        return car;
    }

    @Override
    public Car update(Car car) {
        Document newD = null;
        try {
            newD = Document.parse((new ObjectMapper()).writeValueAsString(car));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
        Document old = new Document("_id", car.get_id());
        collection.updateOne(old, newD);
        return car;
    }

    @Override
    public Car delete(Car car) {
        Document toDelete = new Document();
        toDelete.append("_id", car.get_id());
        collection.deleteOne(toDelete);
        car.set_id(null);
        return car;
    }
}
