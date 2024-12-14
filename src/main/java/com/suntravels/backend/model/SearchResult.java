package com.suntravels.backend.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class SearchResult {

    public SearchResult( List<RoomType> roomType, BigDecimal price, String availabilityStatus,String hotelName )
    {
        this.roomType = roomType;
        this.price = price;
        this.availabilityStatus = availabilityStatus;
        this.hotelName = hotelName;
    }

    public SearchResult()
    {
    }

    public List<RoomType> getRoomType()
    {
        return roomType;
    }

    public void setRoomType( List<RoomType> roomType )
    {
        this.roomType = roomType;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice( BigDecimal price )
    {
        this.price = price;
    }

    public String getAvailabilityStatus()
    {
        return availabilityStatus;
    }

    public void setAvailabilityStatus( String availabilityStatus )
    {
        this.availabilityStatus = availabilityStatus;
    }

    private List<RoomType> roomType;
    private BigDecimal price;
    private String availabilityStatus;

    public String getHotelName()
    {
        return hotelName;
    }

    public void setHotelName( String hotelName )
    {
        this.hotelName = hotelName;
    }

    private String hotelName;

    @Override
    public String toString()
    {
        return "SearchResult{" +
                       "roomType=" + roomType.stream().map( RoomType::toString ).toList() +
                       ", price=" + price +
                       ", availabilityStatus='" + availabilityStatus + '\'' +
                       ", hotelName='" + hotelName + '\'' +
                       '}';
    }
}

