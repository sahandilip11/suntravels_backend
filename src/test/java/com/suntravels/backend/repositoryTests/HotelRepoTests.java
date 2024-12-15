package com.suntravels.backend.repositoryTests;

import com.suntravels.backend.model.Hotel;
import com.suntravels.backend.repository.HotelRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test") // Activates the application-test.properties configuratio
public class HotelRepoTests {

    @Autowired
    private HotelRepo hotelRepo;

    @Test
    void testSaveAndFindByHotelName() {
        // Arrange
        Hotel hotel = new Hotel();
        hotel.setHotelName("TestHotel");
        hotelRepo.save(hotel);

        // Act
        Optional<Hotel> foundHotel = hotelRepo.findByHotelName("TestHotel");

        // Assert
        assertTrue(foundHotel.isPresent());
        assertEquals("TestHotel", foundHotel.get().getHotelName());
    }

    @Test
    void testFindByHotelNameNotFound() {
        // Act
        Optional<Hotel> foundHotel = hotelRepo.findByHotelName("NonExistentHotel");

        // Assert
        assertFalse(foundHotel.isPresent());
    }
}
