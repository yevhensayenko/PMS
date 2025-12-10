package org.yevhens.parkinglot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.yevhens.parkinglot.entity.embeddable.ParkingSpotId;

@Getter
@Setter
@Entity
@Table(name = "parking_spot")
public class ParkingSpot {

    @EmbeddedId
    private ParkingSpotId id;

    @MapsId("parkingLotId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_id")
    private ParkingLot parkingLot;


    @Column
    private String vehicleType;

    @Column
    private boolean available;


}