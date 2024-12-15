package com.suntravels.backend.serviceTests;

import com.suntravels.backend.dto.HotelDto;
import com.suntravels.backend.model.Hotel;
import com.suntravels.backend.repository.HotelRepo;
import com.suntravels.backend.service.HotelContractService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HotelContractsServiceTests {

    @Mock
    private HotelRepo hotelRepo;

    @InjectMocks
    private HotelContractService hotelService;

    private Hotel hotel;

    @BeforeEach
    void setUp() {
        hotel = new Hotel();
        hotel.setHotelName("TestHotel");
    }

    @Test
    void testFindByHotelName() {
        String hotelName = "TestHotel";

        when(hotelRepo.findByHotelName(hotelName)).thenReturn(Optional.of(hotel));

        HotelDto result = hotelService.findByHotelName(hotelName);

        assertEquals(hotelName, result.getHotelName());
        verify(hotelRepo, times(1)).findByHotelName(hotelName);
    }
}
