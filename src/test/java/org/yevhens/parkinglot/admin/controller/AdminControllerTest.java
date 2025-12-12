package org.yevhens.parkinglot.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.yevhens.parkinglot.admin.dto.ChangeAvailabilityDto;
import org.yevhens.parkinglot.admin.dto.ParkingLevelCreateRequest;
import org.yevhens.parkinglot.admin.dto.ParkingLevelDto;
import org.yevhens.parkinglot.admin.dto.ParkingLotCreateRequest;
import org.yevhens.parkinglot.admin.dto.ParkingSpotCreateRequest;
import org.yevhens.parkinglot.entity.spot.ParkingSpot;
import org.yevhens.parkinglot.model.ParkingSpotType;
import org.yevhens.parkinglot.repository.ParkingLevelRepository;
import org.yevhens.parkinglot.repository.ParkingLotRepository;
import org.yevhens.parkinglot.repository.ParkingSpotRepository;
import org.yevhens.parkinglot.service.ParkingLevelService;
import org.yevhens.parkinglot.service.ParkingLotService;
import org.yevhens.parkinglot.service.ParkingSpotService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ParkingLotService parkingLotService;

    @Autowired
    private ParkingLevelService parkingLevelService;

    @Autowired
    private ParkingSpotService parkingSpotService;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ParkingLevelRepository parkingLevelRepository;

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    private Long parkingLotId;

    @BeforeEach
    void setUp() {
        parkingLotId = parkingLotService.registerParkingLot(new ParkingLotCreateRequest("Test Parking Lot")).id();
    }

    @Test
    void registerParkingLot() throws Exception {
        mockMvc.perform(post("/api/v1/admin/parking-lots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ParkingLotCreateRequest("Central Parking"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Central Parking"));

        assertThat(parkingLotRepository.findAll()).hasSize(2);
    }

    @Test
    void registerParkingLotInvalidShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/admin/parking-lots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ParkingLotCreateRequest(" "))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[*].field", containsInAnyOrder("name")));
    }

    @Test
    void addParkingLevel() throws Exception {
        mockMvc.perform(post("/api/v1/admin/parking-lots/{id}/levels", parkingLotId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ParkingLevelCreateRequest(2))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.parkingLotId").value(parkingLotId))
                .andExpect(jsonPath("$.parkingLevel").value(2));

        assertThat(parkingLevelRepository.findAll()).hasSize(1);
    }

    @Test
    void addParkingLevelInvalidShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/admin/parking-lots/{id}/levels", parkingLotId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ParkingLevelCreateRequest(0))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[*].field", containsInAnyOrder("level")));
    }

    @Test
    void registerParkingSpot() throws Exception {
        ParkingLevelDto level = parkingLevelService.addParkingLevel(parkingLotId, new ParkingLevelCreateRequest(1));

        mockMvc.perform(post("/api/v1/admin/parking-lots/{id}/levels/{level}/parking-spots", parkingLotId, level.parkingLevel())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ParkingSpotCreateRequest(7, ParkingSpotType.COMPACT, true))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.parkingLotId").value(parkingLotId))
                .andExpect(jsonPath("$.level").value(1))
                .andExpect(jsonPath("$.parkingSpotNo").value(7))
                .andExpect(jsonPath("$.available").value(true));

        assertThat(parkingSpotRepository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("Test toggle availability")
    void changeAvailability() throws Exception {
        parkingLevelService.addParkingLevel(parkingLotId, new ParkingLevelCreateRequest(1));
        parkingSpotService.registerParkingSpot(parkingLotId, 1, new ParkingSpotCreateRequest(12, ParkingSpotType.COMPACT, true));

        mockMvc.perform(patch("/api/v1/admin/parking-lots/{id}/levels/{level}/parking-spots/{spot}", parkingLotId, 1, 12)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ChangeAvailabilityDto(false))))
                .andExpect(status().isOk());

        assertThat(parkingSpotRepository.findAll())
                .singleElement()
                .extracting(ParkingSpot::isAvailable)
                .isEqualTo(false);
    }

    @Test
    @DisplayName("DELETE endpoints remove created resources")
    void deleteResources() throws Exception {
        ParkingLevelDto level = parkingLevelService.addParkingLevel(parkingLotId, new ParkingLevelCreateRequest(3));
        parkingSpotService.registerParkingSpot(parkingLotId, level.parkingLevel(), new ParkingSpotCreateRequest(21, ParkingSpotType.COMPACT, true));

        mockMvc.perform(delete("/api/v1/admin/parking-lots/{id}/levels/{level}/parking-spots/{spot}", parkingLotId, level.parkingLevel(), 21))
                .andExpect(status().isNoContent());
        assertThat(parkingSpotRepository.findAll()).isEmpty();

        mockMvc.perform(delete("/api/v1/admin/parking-lots/{id}/levels/{level}", parkingLotId, level.parkingLevel()))
                .andExpect(status().isNoContent());
        assertThat(parkingLevelRepository.findAll()).isEmpty();

        mockMvc.perform(delete("/api/v1/admin/parking-lots/{id}", parkingLotId))
                .andExpect(status().isOk());
        assertThat(parkingLotRepository.findAll()).isEmpty();
    }
}