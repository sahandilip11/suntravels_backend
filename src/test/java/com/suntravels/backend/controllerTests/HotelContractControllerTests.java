// Importing required packages and classes for testing
package com.suntravels.backend.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper; // Used for JSON serialization/deserialization
import com.suntravels.backend.controller.HotelContractController; // Controller to be tested
import com.suntravels.backend.dto.HotelContractDto; // DTO class representing the data structure
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

// Importing static methods for Mockito and MockMvc testing
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Annotation to use MockitoExtension for mocking
@ExtendWith(MockitoExtension.class)
public class HotelContractControllerTests {

    // Mocking the service layer
    @Mock
    private HotelContractService hotelService;

    // Injecting the mock service into the controller
    @InjectMocks
    private HotelContractController hotelController;

    // MockMvc to simulate HTTP requests to the controller
    private MockMvc mockMvc;

    // ObjectMapper for converting Java objects to JSON
    private ObjectMapper objectMapper;

    // Setting up MockMvc and ObjectMapper before each test
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(hotelController).build(); // Standalone setup for MockMvc
        objectMapper = new ObjectMapper(); // Initialize ObjectMapper
    }

    // Test for the addContract method in the controller
    @Test
    void testAddContract() throws Exception {
        // Creating a sample HotelContractDto object with test data
        HotelContractDto contractDto = new HotelContractDto();
        contractDto.setHotelName("TestHotel"); // Setting hotel name for test

        // Defining the mock behavior of the service layer
        when(hotelService.addContract(any())).thenReturn(contractDto);

        // Simulating a POST request to the /api/v1/contracts endpoint
        mockMvc.perform(post("/api/v1/contracts") // Specify the endpoint
                                                  .contentType(MediaType.APPLICATION_JSON) // Specify content type as JSON
                                                  .content(objectMapper.writeValueAsString(contractDto))) // Convert DTO to JSON
               .andExpect(status().isOk()); // Expect HTTP 200 OK response

        // Verifying that the service method addContract was called exactly once
        verify(hotelService, times(1)).addContract(any());
    }
}
