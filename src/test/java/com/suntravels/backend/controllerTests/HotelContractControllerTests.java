package com.suntravels.backend.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper; // Used for JSON serialization/deserialization
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; // Module for handling Java 8 Date/Time types
import com.fasterxml.jackson.databind.SerializationFeature;
import com.suntravels.backend.controller.HotelContractController; // Controller to be tested
import com.suntravels.backend.dto.HotelContractDto; // DTO class representing the data structure
import com.suntravels.backend.dto.RoomTypeDto; // RoomType DTO class
import com.suntravels.backend.service.HotelContractService; // Service layer dependency to be mocked
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension; // Mockito extension for testing
import org.springframework.http.MediaType; // MediaType for specifying request/response types
import org.springframework.test.web.servlet.MockMvc; // MockMvc for simulating HTTP requests
import org.springframework.test.web.servlet.setup.MockMvcBuilders; // For setting up MockMvc

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for the {@link HotelContractController} class.
 * Tests the functionality of the addContract API endpoint using MockMvc and a mocked HotelContractService.
 */
@ExtendWith(MockitoExtension.class) // Enables Mockito support for the test class
public class HotelContractControllerTests {

    /**
     * Mocked service layer to isolate controller behavior.
     */
    @Mock
    private HotelContractService hotelService;

    /**
     * Injected instance of the controller under test.
     */
    @InjectMocks
    private HotelContractController hotelController;

    /**
     * MockMvc for simulating HTTP requests and responses.
     */
    private MockMvc mockMvc;

    /**
     * ObjectMapper for converting Java objects to JSON during tests.
     */
    private ObjectMapper objectMapper;

    /**
     * Sets up the MockMvc instance and configures the ObjectMapper for handling Java 8 date/time types.
     * This method is executed before each test.
     */
    @BeforeEach
    void setUp() {
        // Initialize MockMvc with the standalone setup of the controller
        mockMvc = MockMvcBuilders.standaloneSetup(hotelController).build();

        // Initialize ObjectMapper and configure it for Java 8 date/time types
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Register JavaTimeModule for LocalDate
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Use ISO-8601 date format
    }

    /**
     * Tests the addContract endpoint of the {@link HotelContractController}.
     * Verifies that a valid request is processed successfully and returns an HTTP 200 response.
     *
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    void testAddContract() throws Exception {
        // Create sample RoomTypeDto objects with valid data
        RoomTypeDto roomType1 = new RoomTypeDto(
                1L, // Contract ID
                "Deluxe Room", // Room type name
                new BigDecimal("150.0"), // Price per person
                10, // Number of rooms
                2 // Max number of adults
        );

        RoomTypeDto roomType2 = new RoomTypeDto(
                1L, // Contract ID
                "Suite", // Room type name
                new BigDecimal("250.0"), // Price per person
                5, // Number of rooms
                4 // Max number of adults
        );

        // Create a sample HotelContractDto object with all required test data
        HotelContractDto contractDto = new HotelContractDto();
        contractDto.setHotelName("TestHotel"); // Hotel name
        contractDto.setValidFrom(LocalDate.now()); // Contract validity start date
        contractDto.setValidTo(LocalDate.now().plusDays(30)); // Contract validity end date
        contractDto.setMarkupRate(new BigDecimal("10.5")); // Markup rate
        contractDto.setRoomTypeList(List.of(roomType1, roomType2)); // List of room types

        // Mock the service layer's behavior
        when(hotelService.addContract(any())).thenReturn(contractDto);

        // Simulate a POST request to the /api/v1/contracts endpoint
        mockMvc.perform(post("/api/v1/contracts") // Specify the endpoint
                                                  .contentType(MediaType.APPLICATION_JSON) // Set content type as JSON
                                                  .content(objectMapper.writeValueAsString(contractDto))) // Convert DTO to JSON
               .andExpect(status().isOk()); // Verify the response status is 200 OK

        // Verify that the service method addContract was called exactly once
        verify(hotelService, times(1)).addContract(any());
    }
}
