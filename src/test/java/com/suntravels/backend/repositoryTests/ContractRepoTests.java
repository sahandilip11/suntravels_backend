package com.suntravels.backend.repositoryTests;

import com.suntravels.backend.model.Contract;
import com.suntravels.backend.model.Hotel;
import com.suntravels.backend.model.RoomType;
import com.suntravels.backend.repository.ContractRepo;
import com.suntravels.backend.repository.HotelRepo;
import com.suntravels.backend.repository.RoomTypeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith( SpringExtension.class )
@DataJpaTest
@ActiveProfiles( "test" )
public class ContractRepoTests
{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private HotelRepo hotelRepo;

    @Autowired
    private ContractRepo contractRepo;

    @Autowired
    private RoomTypeRepo roomTypeRepo;

    @BeforeEach
    void setupDatabase()
    {
        String schemaSql = """
                    CREATE TABLE IF NOT EXISTS hotel (
                        hotel_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        hotel_name VARCHAR(50) NOT NULL
                    );
                
                    CREATE TABLE IF NOT EXISTS contract (
                        contract_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        mark_up_rate DECIMAL(5, 2) NOT NULL,
                        valid_from DATE NOT NULL,
                        valid_to DATE NULL,
                        hotel_id BIGINT NOT NULL,
                        CONSTRAINT FKa7ptivthfrs7mn1algfbys0yy FOREIGN KEY (hotel_id)
                            REFERENCES hotel (hotel_id)
                    );
                
                    CREATE TABLE IF NOT EXISTS room_type (
                        room_type_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        max_no_of_adults INT NOT NULL,
                        no_of_rooms INT NOT NULL,
                        per_person_price DECIMAL(10, 2) NOT NULL,
                        type_name VARCHAR(50) NOT NULL,
                        contract_id BIGINT NOT NULL,
                        CONSTRAINT FKl0jaennl6w4usknvft3gucces FOREIGN KEY (contract_id)
                            REFERENCES contract (contract_id)
                    );
                """;
        jdbcTemplate.execute( schemaSql );
    }


    @Test
    void testSaveAndRetrieveHotel()
    {
        // Arrange
        Hotel hotel = new Hotel();
        hotel.setHotelName( "TestHotel" );

        // Act
        Hotel savedHotel = hotelRepo.save( hotel );

        // Assert
        Optional<Hotel> retrievedHotel = hotelRepo.findById( savedHotel.getHotelId() );
        assertTrue( retrievedHotel.isPresent() );
        assertEquals( "TestHotel", retrievedHotel.get().getHotelName() );
    }

    @Test
    void testSaveContract()
    {
        // Arrange
        Hotel hotel = new Hotel();
        hotel.setHotelName( "TestHotel2" );
        Hotel savedHotel = hotelRepo.save( hotel );

        Contract contract = new Contract();
        contract.setHotel( savedHotel );
        contract.setMarkupRate( BigDecimal.valueOf( 1.5 ) );
        contract.setValidFrom( LocalDate.of( 2024, 12, 1 ) );
        contract.setValidTo( LocalDate.of( 2024, 12, 31 ) );

        // Act
        Contract savedContract = contractRepo.save( contract );

        // Assert
        assertNotNull( savedContract.getContractId() );
        assertEquals( savedHotel.getHotelId(), savedContract.getHotel().getHotelId() );
        assertEquals( BigDecimal.valueOf( 1.5 ), savedContract.getMarkupRate() );
        assertEquals( LocalDate.of( 2024, 12, 1 ), savedContract.getValidFrom() );
        assertEquals( LocalDate.of( 2024, 12, 31 ), savedContract.getValidTo() );
    }

    @Test
    void testSaveRoomType()
    {
        // Arrange
        Hotel hotel = new Hotel();
        hotel.setHotelName( "TestHotel3" );
        Hotel savedHotel = hotelRepo.save( hotel );

        Contract contract = new Contract();
        contract.setHotel( savedHotel );
        contract.setMarkupRate( BigDecimal.valueOf( 2.0 ) );
        contract.setValidFrom( LocalDate.of( 2024, 12, 1 ) );
        contract.setValidTo( LocalDate.of( 2024, 12, 31 ) );
        Contract savedContract = contractRepo.save( contract );

        RoomType roomType = new RoomType();
        roomType.setContract( savedContract );
        roomType.setTypeName( "Deluxe" );
        roomType.setMaxNoAdults( 2 );
        roomType.setNoOfRooms( 10 );
        roomType.setPerPersonPrice( BigDecimal.valueOf( 150.00 ) );

        // Act
        RoomType savedRoomType = roomTypeRepo.save( roomType );

        // Assert
        assertNotNull( savedRoomType.getRoomTypeId() );
        assertEquals( "Deluxe", savedRoomType.getTypeName() );
        assertEquals( 2, savedRoomType.getMaxNoAdults() );
        assertEquals( 10, savedRoomType.getNoOfRooms() );
        assertEquals( BigDecimal.valueOf( 150.00 ), savedRoomType.getPerPersonPrice() );
    }
}
