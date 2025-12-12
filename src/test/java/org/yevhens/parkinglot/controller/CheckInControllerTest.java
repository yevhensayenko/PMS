package org.yevhens.parkinglot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.yevhens.parkinglot.entity.ParkingSpotDto;
import org.yevhens.parkinglot.model.CheckInDto;
import org.yevhens.parkinglot.model.CheckOutDto;
import org.yevhens.parkinglot.model.CheckOutResponse;
import org.yevhens.parkinglot.model.Receipt;
import org.yevhens.parkinglot.model.VehicleDto;
import org.yevhens.parkinglot.model.VehicleType;
import org.yevhens.parkinglot.service.CheckInService;
import org.yevhens.parkinglot.service.CheckOutService;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CheckInController.class)
class CheckInControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CheckInService checkInService;

    @MockitoBean
    private CheckOutService checkOutService;

    @Test
    @DisplayName("POST /api/v1/check-in returns receipt when request is valid")
    void checkInReturnsReceipt() throws Exception {
        var request = new CheckInDto(1L, "TEST-123", VehicleType.CAR, false);
        Receipt response = Receipt.builder()
                .id(10L)
                .vehicle(new VehicleDto(request.licensePlate()))
                .parkingSpot(new ParkingSpotDto(1L, 2, 15, true))
                .startDateTime(Instant.parse("2024-01-01T10:15:30Z"))
                .build();

        when(checkInService.checkIn(request)).thenReturn(response);

        mockMvc.perform(post("/api/v1/check-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.vehicle.licensePlate").value("TEST-123"))
                .andExpect(jsonPath("$.parkingSpot.parkingLotId").value(1))
                .andExpect(jsonPath("$.parkingSpot.level").value(2))
                .andExpect(jsonPath("$.parkingSpot.parkingSpotNo").value(15));

        verify(checkInService).checkIn(request);
    }

    @Test
    @DisplayName("POST /api/v1/check-out returns calculated fee and timing")
    void checkOutReturnsFee() throws Exception {
        var request = new CheckOutDto(22L);
        CheckOutResponse response = new CheckOutResponse(
                22L,
                Instant.parse("2024-01-01T10:15:30Z"),
                Instant.parse("2024-01-01T12:45:30Z"),
                Duration.ofMinutes(150),
                new BigDecimal("25.50")
        );

        when(checkOutService.checkOut(request)).thenReturn(response);

        mockMvc.perform(post("/api/v1/check-out")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.receiptId").value(22))
                .andExpect(jsonPath("$.fee").value(25.50))
                .andExpect(jsonPath("$.duration").value("PT2H30M"))
                .andExpect(jsonPath("$.entryTime").value("2024-01-01T10:15:30Z"))
                .andExpect(jsonPath("$.exitTime").value("2024-01-01T12:45:30Z"));

        verify(checkOutService).checkOut(request);
    }
}