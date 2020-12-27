package ru.itis.mongo.repository.search;

import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import ru.itis.mongo.model.Driver;
import ru.itis.mongo.model.QDriver;

public interface DriverRepository extends MongoRepository<Driver, String>, QuerydslPredicateExecutor<Driver>,
        QuerydslBinderCustomizer<QDriver> {

    @Override
    default void customize(QuerydslBindings bindings, QDriver qDriver) {
        bindings.bind(qDriver.cars.any().colour).as("cars.color").first(
                StringExpression::containsIgnoreCase
        );
    }
}
