package com.suntravels.backend.dto;

public class HotelDto
{

    private Long hotelId;
    private String hotelName;

    public HotelDto( String hotelName )
    {
        this.hotelName = hotelName;
    }


    public Long getHotelId()
    {
        return hotelId;
    }

    public void setHotelId( Long hotelId )
    {
        this.hotelId = hotelId;
    }

    public String getHotelName()
    {
        return hotelName;
    }

    public void setHotelName( String hotelName )
    {
        this.hotelName = hotelName;
    }

    public HotelDto( String hotelName, Long hotelId )
    {
        this.hotelName = hotelName;
        this.hotelId = hotelId;
    }

    public HotelDto()
    {
    }

}
