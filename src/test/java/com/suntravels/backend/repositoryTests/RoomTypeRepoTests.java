package com.suntravels.backend.repositoryTests;

import com.suntravels.backend.model.Contract;
import com.suntravels.backend.model.Hotel;
import com.suntravels.backend.model.RoomType;
import com.suntravels.backend.repository.ContractRepo;
import com.suntravels.backend.repository.HotelRepo;
import com.suntravels.backend.repository.RoomTypeRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test") // Activates the application-test.properties configuratio
public class RoomTypeRepoTests {

    @Autowired
    private RoomTypeRepo roomTypeRepo;

    @Autowired
    private HotelRepo hotelRepo;

    @Autowired
    private ContractRepo contractRepo;

    @Test
    void testSaveRoomType() {
        // Arrange
        // Step 1: Create and save a Hotel
        Hotel hotel = new Hotel();
        hotel.setHotelName("TestHotel3");
        Hotel savedHotel = hotelRepo.save(hotel);

        // Step 2: Create and save a Contract for the Hotel
        Contract contract = new Contract();
        contract.setHotel(savedHotel);
        contract.setMarkupRate(BigDecimal.valueOf(2.0));
        contract.setValidFrom( LocalDate.of(2024, 12, 1));
        contract.setValidTo(LocalDate.of(2024, 12, 31));
        Contract savedContract = contractRepo.save(contract);

        // Step 3: Create and save a RoomType for the Contract
        RoomType roomType = new RoomType();
        roomType.setContract(savedContract); // Associate the valid Contract
        roomType.setTypeName("Deluxe");
        roomType.setMaxNoAdults(2);
        roomType.setNoOfRooms(10);
        roomType.setPerPersonPrice(BigDecimal.valueOf(150.00));

        // Act
        RoomType savedRoomType = roomTypeRepo.save(roomType);

        // Assert
        assertNotNull(savedRoomType.getRoomTypeId());
        assertEquals("Deluxe", savedRoomType.getTypeName());
        assertEquals(2, savedRoomType.getMaxNoAdults());
        assertEquals(10, savedRoomType.getNoOfRooms());
        assertEquals(BigDecimal.valueOf(150.00), savedRoomType.getPerPersonPrice());
        assertEquals(savedContract.getContractId(), savedRoomType.getContract().getContractId());
    }
}

