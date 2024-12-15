package com.suntravels.backend.repositoryTests;

import com.suntravels.backend.model.RoomType;
import com.suntravels.backend.repository.RoomTypeRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test") // Activates the application-test.properties configuratio
public class RoomTypeRepoTests {

    @Autowired
    private RoomTypeRepo roomTypeRepo;

    @Test
    void testSaveRoomType() {
        // Arrange
        RoomType roomType = new RoomType();
        roomType.setTypeName("Deluxe");
        roomType.setMaxNoAdults(2);
        roomType.setPerPersonPrice( BigDecimal.valueOf( 150.0 ) );

        // Act
        RoomType savedRoomType = roomTypeRepo.save(roomType);

        // Assert
        assertNotNull(savedRoomType.getRoomTypeId());
        assertEquals("Deluxe", savedRoomType.getTypeName());
        assertEquals(2, savedRoomType.getMaxNoAdults());
        assertEquals(150.0, savedRoomType.getPerPersonPrice());
    }
}
