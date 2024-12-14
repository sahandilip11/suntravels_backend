package com.suntravels.backend.repository;

import com.suntravels.backend.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelRepo extends JpaRepository<Hotel, Long>
{
    Optional<Hotel> findByHotelName( String hotelName );
}
