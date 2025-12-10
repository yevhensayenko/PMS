package org.yevhens.parkinglot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.yevhens.parkinglot.model.VehicleType;

@Getter
@Setter
@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @NotNull
    @Column(nullable = false)
    private String licensePlate;

    @NotNull
    @Column(nullable = false)
    private VehicleType vehicleType;

}