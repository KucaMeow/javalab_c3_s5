package ru.itis.hm3.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany
    @JoinTable(name = "service_storage", joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "wheel_id"))
    private List<Wheel> wheels;

    public void addWheel(Wheel wheel) {
        wheels.add(wheel);
    }

    public void addWheels(Collection<Wheel> wheels) {
        this.wheels.addAll(wheels);
    }
}
