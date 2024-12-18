package com.suntravels.backend.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.suntravels.backend.controller.SearchController;
import com.suntravels.backend.model.RoomRequest;
import com.suntravels.backend.model.SearchRequest;
import com.suntravels.backend.model.SearchResult;
import com.suntravels.backend.repository.ContractRepo;
import com.suntravels.backend.service.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for the {@link SearchController} class.
 * Tests the functionality of the search API endpoint using MockMvc and a mocked SearchService.
 */
@WebMvcTest(SearchController.class)
@Import(SearchControllerTests.TestConfig.class) // Import the test-specific configuration
public class SearchControllerTests {

    @Autowired
    private MockMvc mockMvc; // MockMvc to perform requests to the controller

    @Autowired
    private SearchService searchService; // Mocked SearchService

    @Autowired
    private ObjectMapper objectMapper; // ObjectMapper for JSON serialization/deserialization

    /**
     * Sets up the test environment by configuring the ObjectMapper.
     * This ensures proper handling of Java 8 date/time types like {@link LocalDate}.
     */
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        // Register JavaTimeModule for handling LocalDate
        objectMapper.registerModule(new JavaTimeModule());

        // Disable serialization of dates as timestamps
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    /**
     * Tests the searchRooms endpoint with a valid {@link SearchRequest}.
     * Verifies the endpoint returns the expected {@link SearchResult}.
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    void testSearchRooms() throws Exception {
        // Create a valid SearchRequest
        SearchRequest searchRequest = new SearchRequest(
                LocalDate.of(2024, 12, 20), // Check-in date
                5, // Number of nights
                List.of(new RoomRequest(2, 2)) // Room requests
        );

        // Mock the service response
        SearchResult mockResult = new SearchResult(
                null, // Room type details (not needed for this test)
                BigDecimal.valueOf(1500), // Price
                "Available", // Availability status
                "TestHotel" // Hotel name
        );

        // Configure the mock service behavior
        Mockito.when(searchService.searchRooms(Mockito.any(SearchRequest.class)))
               .thenReturn(List.of(mockResult));

        // Perform the POST request to the search endpoint and verify the response
        mockMvc.perform(post("/api/v1/search")
                                .contentType(MediaType.APPLICATION_JSON) // Set content type to JSON
                                .content(objectMapper.writeValueAsString(searchRequest))) // Serialize the request to JSON
               .andExpect(status().isOk()) // Expect HTTP 200 OK status
               .andExpect(jsonPath("$.length()").value(1)) // Expect one result in the response
               .andExpect(jsonPath("$[0].hotelName").value("TestHotel")) // Verify hotel name
               .andExpect(jsonPath("$[0].availabilityStatus").value("Available")) // Verify availability status
               .andExpect(jsonPath("$[0].price").value(1500.0)); // Verify price
    }

    /**
     * Test configuration for injecting mock beans into the test context.
     */
    @TestConfiguration
    static class TestConfig {

        /**
         * Provides a mock implementation of {@link SearchService}.
         *
         * @return the mocked SearchService instance
         */
        @Bean
        public SearchService searchService() {
            return Mockito.mock(SearchService.class);
        }

        /**
         * Provides a mock implementation of {@link ContractRepo}.
         *
         * @return the mocked ContractRepo instance
         */
        @Bean
        public ContractRepo contractRepo() {
            return Mockito.mock(ContractRepo.class);
        }
    }
}
