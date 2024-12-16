package com.suntravels.backend.integratonTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suntravels.backend.dto.HotelContractDto;
import com.suntravels.backend.dto.RoomTypeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class HotelContractIntegrationTests
{

    @Autowired
    private TestRestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testAddAndFetchContracts()
    {
        // Prepare a new contract with RoomTypeDto
        RoomTypeDto roomTypeDto = new RoomTypeDto(
                null,  // contractId is null since it's not assigned yet
                "Deluxe",
                BigDecimal.valueOf( 150.0 ),
                10, // Number of rooms
                2   // Max number of adults
        );

        HotelContractDto contractDto = new HotelContractDto();
        contractDto.setHotelName( "TestHotel" );
        contractDto.setValidFrom( LocalDate.now() );
        contractDto.setValidTo( LocalDate.now().plusDays( 10 ) );
        contractDto.setMarkupRate( BigDecimal.valueOf( 10.5 ) );
        contractDto.setRoomTypeList( Collections.singletonList( roomTypeDto ) );

        // Add Contract
        ResponseEntity<HotelContractDto> response = restTemplate.postForEntity(
                "/api/v1/contracts",
                contractDto,
                HotelContractDto.class );

        assertEquals( HttpStatus.OK, response.getStatusCode() );
        assertEquals( "TestHotel", response.getBody().getHotelName() );

        // Fetch All Contracts
        ResponseEntity<HotelContractDto[]> fetchResponse = restTemplate.getForEntity(
                "/api/v1/contracts",
                HotelContractDto[].class );

        assertEquals( HttpStatus.OK, fetchResponse.getStatusCode() );

        // Verify TestHotel exists in the fetched list
        boolean testHotelFound = false;
        for( HotelContractDto fetchedContract : fetchResponse.getBody() )
        {
            if( "TestHotel".equals( fetchedContract.getHotelName() ) )
            {
                testHotelFound = true;

                // Additional assertions to validate the room type
                assertEquals( "Deluxe", fetchedContract.getRoomTypeList().get( 0 ).getTypeName() );
                assertEquals( 10, fetchedContract.getRoomTypeList().get( 0 ).getNoOfRooms() );
                break; // No need to iterate further once the TestHotel is found
            }
        }

        // Assert that TestHotel was found
        assertEquals( true, testHotelFound, "Expected 'TestHotel' to be present in the fetched contracts." );
    }
}
