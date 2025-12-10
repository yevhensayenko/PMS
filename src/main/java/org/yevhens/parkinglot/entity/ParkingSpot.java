package org.yevhens.parkinglot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yevhens.parkinglot.entity.embeddable.ParkingSpotId;
import org.yevhens.parkinglot.model.ParkingSpotType;

@Getter
@Setter
@Entity
@Builder
@Table(name = "parking_spot")
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpot {

    @EmbeddedId
    private ParkingSpotId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_id", insertable = false, updatable = false)
    private ParkingLot parkingLot;

    @MapsId("parkingLevelId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "parking_lot_id"),
            @JoinColumn(name = "parking_level")
    })
    private ParkingLevel parkingLevel;


    @Column
    private ParkingSpotType type;

    @Column
    private boolean available;


}