package org.yevhens.parkinglot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.yevhens.parkinglot.entity.ParkingLot;
import org.yevhens.parkinglot.entity.embeddable.ParkingSpotId;
import org.yevhens.parkinglot.entity.spot.CompactSpot;
import org.yevhens.parkinglot.entity.spot.HandicappedSpot;
import org.yevhens.parkinglot.entity.spot.ParkingSpot;
import org.yevhens.parkinglot.entity.vehicle.Car;
import org.yevhens.parkinglot.entity.vehicle.Motorcycle;
import org.yevhens.parkinglot.repository.ParkingSpotRepository;
import org.yevhens.parkinglot.service.assignment.CarSlotAssignmentStrategy;
import org.yevhens.parkinglot.service.assignment.MotorcycleSlotAssignmentStrategy;
import org.yevhens.parkinglot.service.assignment.TruckSlotAssignmentStrategy;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpotAssignmentServiceTest {

    @Mock
    private ParkingSpotRepository parkingSpotRepository;

    private SpotAssignmentService spotAssignmentService;

    private final ParkingLot parkingLot = ParkingLot.builder().id(1L).name("Spot1").build();

    @BeforeEach
    void setUp() {
        spotAssignmentService = new SpotAssignmentService(
                List.of(
                        new CarSlotAssignmentStrategy(parkingSpotRepository),
                        new MotorcycleSlotAssignmentStrategy(parkingSpotRepository),
                        new TruckSlotAssignmentStrategy(parkingSpotRepository)
                ),
                parkingSpotRepository
        );
    }

    @Test
    void assignSpotCarNotHandicappedHappyPath() {
        Car car = Car.builder().handicapped(false).build();
        car.setLicensePlate("HANDI-1");

        CompactSpot spot = CompactSpot.builder()
                .id(new ParkingSpotId(1L, 1, 10))
                .available(true)
                .build();

        when(parkingSpotRepository.findAvailableCompactSpots(parkingLot.getId()))
                .thenReturn(List.of(spot));
        when(parkingSpotRepository.save(any(ParkingSpot.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Optional<? extends ParkingSpot> assignedSpot = spotAssignmentService.assignSpot(car, parkingLot);

        assertThat(assignedSpot).isPresent();
        assertThat(assignedSpot.get()).isSameAs(spot);
        assertThat(assignedSpot.get().isAvailable()).isFalse();
        verify(parkingSpotRepository).save(spot);
        verify(parkingSpotRepository, never()).findAvailableHandicappedSpots(any());
    }

    @Test
    void assignSpotCarHandicappedTest() {
        Car car = Car.builder().handicapped(true).build();
        car.setLicensePlate("HANDI-1");

        HandicappedSpot spot = HandicappedSpot.builder()
                .id(new ParkingSpotId(1L, 1, 10))
                .available(true)
                .build();

        when(parkingSpotRepository.findAvailableHandicappedSpots(parkingLot.getId()))
                .thenReturn(List.of(spot));
        when(parkingSpotRepository.save(any(ParkingSpot.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Optional<? extends ParkingSpot> assignedSpot = spotAssignmentService.assignSpot(car, parkingLot);

        assertThat(assignedSpot).isPresent();
        assertThat(assignedSpot.get()).isSameAs(spot);
        assertThat(assignedSpot.get().isAvailable()).isFalse();
        verify(parkingSpotRepository).save(spot);
    }

    @Test
    void assignSpotFallsBackToCompactSpotForMotorcycleTest() {
        Motorcycle motorcycle = Motorcycle.builder().build();
        motorcycle.setLicensePlate("MOTO-2");

        CompactSpot compactSpot = CompactSpot.builder()
                .id(new ParkingSpotId(1L, 1, 5))
                .available(true)
                .build();

        when(parkingSpotRepository.findAvailableMotorcycleSpots(parkingLot.getId()))
                .thenReturn(List.of());
        when(parkingSpotRepository.findAvailableCompactSpots(parkingLot.getId()))
                .thenReturn(List.of(compactSpot));
        when(parkingSpotRepository.save(any(ParkingSpot.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Optional<? extends ParkingSpot> assigned = spotAssignmentService.assignSpot(motorcycle, parkingLot);

        assertThat(assigned).isPresent();
        assertThat(assigned.get()).isSameAs(compactSpot);
        assertThat(assigned.get().isAvailable()).isFalse();

        verify(parkingSpotRepository).findAvailableMotorcycleSpots(parkingLot.getId());
        verify(parkingSpotRepository).findAvailableCompactSpots(parkingLot.getId());
        verify(parkingSpotRepository, never()).findAvailableLargeSpots(parkingLot.getId());
    }
}