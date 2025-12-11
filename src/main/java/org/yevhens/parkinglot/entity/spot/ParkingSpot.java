package org.yevhens.parkinglot.entity.spot;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yevhens.parkinglot.entity.ParkingLevel;
import org.yevhens.parkinglot.entity.ParkingLot;
import org.yevhens.parkinglot.entity.embeddable.ParkingSpotId;

@Getter
@Setter
@Entity
@Table(name = "parking_spot")
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "type",
        discriminatorType = DiscriminatorType.STRING,
        length = 40
)
public abstract class ParkingSpot {

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
    private boolean available;


}