package com.suntravels.backend.utils;

import com.suntravels.backend.dto.HotelDto;
import com.suntravels.backend.model.Hotel;

public class HotelMapper {
    public static Hotel dtoToEntity(HotelDto hotelDto) {
        Hotel hotel = new Hotel();
        hotel.setHotelName(hotelDto.getHotelName());
        return hotel;
    }
}



