package com.suntravels.backend.model;

import java.time.LocalDate;
import java.util.List;

public class SearchRequest
{
    public SearchRequest( LocalDate checkInDate, int numberOfNights, List<RoomRequest> roomRequests )
    {
        this.checkInDate = checkInDate;
        this.numberOfNights = numberOfNights;
        this.roomRequests = roomRequests;
    }

    public SearchRequest()
    {
    }

    public LocalDate getCheckInDate()
    {
        return checkInDate;
    }

    public void setCheckInDate( LocalDate checkInDate )
    {
        this.checkInDate = checkInDate;
    }

    public int getNumberOfNights()
    {
        return numberOfNights;
    }

    public void setNumberOfNights( int numberOfNights )
    {
        this.numberOfNights = numberOfNights;
    }

    public List<RoomRequest> getRoomRequests()
    {
        return roomRequests;
    }

    public void setRoomRequests( List<RoomRequest> roomRequests )
    {
        this.roomRequests = roomRequests;
    }

    private LocalDate checkInDate;
    private int numberOfNights;
    private List<RoomRequest> roomRequests;

}
