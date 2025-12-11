package org.yevhens.parkinglot.entity.vehicle;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@DiscriminatorValue("CAR")
@AllArgsConstructor
@NoArgsConstructor
public class Car extends Vehicle {

    private boolean handicapped;

}