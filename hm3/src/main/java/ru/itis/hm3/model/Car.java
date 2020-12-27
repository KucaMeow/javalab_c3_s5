package ru.itis.hm3.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "wheel_1_id")
    private Wheel w1;
    @OneToOne
    @JoinColumn(name = "wheel_2_id")
    private Wheel w2;
    @OneToOne
    @JoinColumn(name = "wheel_3_id")
    private Wheel w3;
    @OneToOne
    @JoinColumn(name = "wheel_4_id")
    private Wheel w4;
    @OneToOne
    @JoinColumn(name = "petrol_tank")
    private PetrolTank petrolTank;
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;
}
