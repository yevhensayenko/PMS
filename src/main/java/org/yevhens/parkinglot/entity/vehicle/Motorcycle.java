package org.yevhens.parkinglot.entity.vehicle;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Builder
@DiscriminatorValue("MOTORCYCLE")
@AllArgsConstructor
public class Motorcycle extends Vehicle {

}