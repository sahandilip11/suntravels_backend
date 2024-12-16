package com.suntravels.backend.repository;

import com.suntravels.backend.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRepo extends JpaRepository<Hotel,Long>
{
    Optional<Hotel> findByHotelName( String hotelName );
}
