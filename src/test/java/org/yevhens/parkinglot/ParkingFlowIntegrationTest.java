package org.yevhens.parkinglot;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.yevhens.parkinglot.admin.dto.ParkingLevelCreateRequest;
import org.yevhens.parkinglot.admin.dto.ParkingLevelDto;
import org.yevhens.parkinglot.admin.dto.ParkingLotCreateRequest;
import org.yevhens.parkinglot.admin.dto.ParkingLotDto;
import org.yevhens.parkinglot.admin.dto.ParkingSpotCreateRequest;
import org.yevhens.parkinglot.entity.ParkingSpotDto;
import org.yevhens.parkinglot.entity.embeddable.ParkingSpotId;
import org.yevhens.parkinglot.entity.spot.ParkingSpot;
import org.yevhens.parkinglot.model.CheckInDto;
import org.yevhens.parkinglot.model.CheckOutDto;
import org.yevhens.parkinglot.model.CheckOutResponse;
import org.yevhens.parkinglot.model.ParkingSpotType;
import org.yevhens.parkinglot.model.Receipt;
import org.yevhens.parkinglot.model.VehicleType;
import org.yevhens.parkinglot.repository.ParkingSpotRepository;

import java.time.Duration;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ParkingFlowIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void fullParkingFlowPersistsDataAndReleasesSpot() {
        ParkingLotDto parkingLot = createLot("Test Lot");
        ParkingLevelDto level = createLevel(parkingLot.id(), 1);
        createSpot(parkingLot.id(), level.parkingLevel(), 1, ParkingSpotType.COMPACT, true);
        createSpot(parkingLot.id(), level.parkingLevel(), 2, ParkingSpotType.COMPACT, true);

        ParkingSpotId parkingSpotId = new ParkingSpotId(parkingLot.id(), level.parkingLevel(), 1);
        assertThat(parkingSpotRepository.findById(parkingSpotId)).get().extracting(ParkingSpot::isAvailable).isEqualTo(true);

        ParkingSpotId parkingSpotId2 = new ParkingSpotId(parkingLot.id(), level.parkingLevel(), 2);
        assertThat(parkingSpotRepository.findById(parkingSpotId2)).get().extracting(ParkingSpot::isAvailable).isEqualTo(true);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/parking-lots/{id}/levels/{level}/parking-spots", parkingLot.id(), level.parkingLevel())
                .then()
                .statusCode(200)
                .body("$", Matchers.hasSize(2));

        Receipt receipt = given()
                .contentType(ContentType.JSON)
                .body(new CheckInDto(parkingLot.id(), "TEST-123", VehicleType.CAR, false))
                .when()
                .post("/api/v1/check-in")
                .then()
                .statusCode(201)
                .extract()
                .as(Receipt.class);

        assertThat(receipt.parkingSpot().available()).isFalse();
        assertThat(parkingSpotRepository.findById(parkingSpotId))
                .get().extracting(ParkingSpot::isAvailable).isEqualTo(false);

        CheckOutResponse checkOutResponse = given()
                .contentType(ContentType.JSON)
                .body(new CheckOutDto(receipt.id()))
                .when()
                .post("/api/v1/check-out")
                .then()
                .statusCode(200)
                .extract()
                .as(CheckOutResponse.class);

        assertThat(checkOutResponse.receiptId()).isEqualTo(receipt.id());
        assertThat(checkOutResponse.duration()).isNotNull();
        assertThat(checkOutResponse.duration()).isGreaterThanOrEqualTo(Duration.ZERO);
        assertThat(checkOutResponse.fee()).isNotNull();
        assertThat(parkingSpotRepository.findById(parkingSpotId))
                .get().extracting(ParkingSpot::isAvailable).isEqualTo(true);
    }

    @Test
    void checkInReturnsValidationErrorsForBlankLicensePlate() {
        ParkingLotDto parkingLot = createLot("Validation Lot");
        createLevel(parkingLot.id(), 1);
        createSpot(parkingLot.id(), 1, 1, ParkingSpotType.COMPACT, true);

        given()
                .contentType(ContentType.JSON)
                .body(new CheckInDto(parkingLot.id(), "   ", VehicleType.CAR, false))
                .when()
                .post("/api/v1/check-in")
                .then()
                .statusCode(400)
                .body("message", Matchers.equalTo("Request validation failed"))
                .body("errors.find { it.field == 'licensePlate' }.message", Matchers.notNullValue());
    }

    @Test
    void testCheckInFailsWhenParkingLotMissing() {
        given()
                .contentType(ContentType.JSON)
                .body(new CheckInDto(999L, "MISSING-LOT", VehicleType.CAR, false))
                .when()
                .post("/api/v1/check-in")
                .then()
                .statusCode(404);
    }

    @Test
    void testCheckInRejectsWhenNoAvailableSpots() {
        ParkingLotDto parkingLot = createLot("Capacity Lot");
        createLevel(parkingLot.id(), 1);
        createSpot(parkingLot.id(), 1, 1, ParkingSpotType.COMPACT, true);

        given()
                .contentType(ContentType.JSON)
                .body(new CheckInDto(parkingLot.id(), "FULL-111", VehicleType.CAR, false))
                .when()
                .post("/api/v1/check-in")
                .then()
                .statusCode(201);

        given()
                .contentType(ContentType.JSON)
                .body(new CheckInDto(parkingLot.id(), "FULL-222", VehicleType.CAR, false))
                .when()
                .post("/api/v1/check-in")
                .then()
                .statusCode(406)
                .body("message", Matchers.equalTo("No available spots at this moment"));
    }

    @Test
    void testCheckOutReturnsNotFoundForUnknownReceiptId() {
        given()
                .contentType(ContentType.JSON)
                .body(new CheckOutDto(123456L))
                .when()
                .post("/api/v1/check-out")
                .then()
                .statusCode(404);
    }

    private ParkingLotDto createLot(String name) {
        return given()
                .contentType(ContentType.JSON)
                .body(new ParkingLotCreateRequest(name))
                .when()
                .post("/api/v1/admin/parking-lots")
                .then()
                .statusCode(201)
                .extract()
                .as(ParkingLotDto.class);
    }

    private ParkingLevelDto createLevel(Long lotId, int level) {
        return given()
                .contentType(ContentType.JSON)
                .body(new ParkingLevelCreateRequest(level))
                .when()
                .post("/api/v1/admin/parking-lots/" + lotId + "/levels")
                .then()
                .statusCode(201)
                .extract()
                .as(ParkingLevelDto.class);
    }

    private void createSpot(Long lotId, int level, int number, ParkingSpotType type, boolean available) {
        given()
                .contentType(ContentType.JSON)
                .body(new ParkingSpotCreateRequest(number, type, available))
                .when()
                .post("/api/v1/admin/parking-lots/" + lotId + "/levels/" + level + "/parking-spots")
                .then()
                .statusCode(201)
                .extract()
                .as(ParkingSpotDto.class);
    }
}