package com.suntravels.backend.repositoryTests;

import com.suntravels.backend.model.Contract;
import com.suntravels.backend.model.Hotel;
import com.suntravels.backend.repository.ContractRepo;
import com.suntravels.backend.repository.HotelRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test") // Activates the application-test.properties configuratio
public class ContractRepoTests {

    @Autowired
    private ContractRepo contractRepo;

    @Autowired
    private HotelRepo hotelRepo;

    @Test
    void testSaveContract() {
        // Arrange
        Hotel hotel = new Hotel();
        hotel.setHotelName("TestHotel");
        Hotel savedHotel = hotelRepo.save(hotel);

        Contract contract = new Contract();
        contract.setHotel(savedHotel);
        contract.setValidFrom(LocalDate.of(2024, 12, 1));
        contract.setValidTo(LocalDate.of(2024, 12, 31));
        contract.setMarkupRate(BigDecimal.valueOf(1.2));

        // Act
        Contract savedContract = contractRepo.save(contract);

        // Assert
        assertNotNull(savedContract.getContractId());
        assertEquals(savedHotel.getHotelId(), savedContract.getHotel().getHotelId());
        assertEquals(LocalDate.of(2024, 12, 1), savedContract.getValidFrom());
        assertEquals(LocalDate.of(2024, 12, 31), savedContract.getValidTo());
        assertEquals(BigDecimal.valueOf(1.2), savedContract.getMarkupRate());
    }
}
