
package org.yevhens.parkinglot.entity.vehicle;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@Entity
@DiscriminatorValue("TRUCK")
@AllArgsConstructor
public class Truck extends Vehicle {

}