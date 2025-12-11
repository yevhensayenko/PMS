package org.yevhens.parkinglot.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yevhens.parkinglot.entity.embeddable.ParkingLevelId;
import org.yevhens.parkinglot.entity.spot.ParkingSpot;

import java.util.Collection;

@Getter
@Setter
@Entity
@Builder
@Table(name = "parking_level")
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLevel {

    @EmbeddedId
    private ParkingLevelId id;

    @MapsId("parkingLotId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_id")
    private ParkingLot parkingLot;


    @OneToMany(mappedBy = "parkingLevel")
    private Collection<ParkingSpot> parkingSpots;

}