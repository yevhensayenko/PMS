
package org.yevhens.parkinglot.entity.vehicle;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TRUCK")
public class Truck extends Vehicle {

}