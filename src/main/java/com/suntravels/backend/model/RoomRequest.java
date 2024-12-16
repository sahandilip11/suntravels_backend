package com.suntravels.backend.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class RoomRequest
{
    public RoomRequest( int numberOfRooms, int numberOfAdults )
    {
        this.numberOfRooms = numberOfRooms;
        this.numberOfAdults = numberOfAdults;
    }

    public RoomRequest()
    {
    }

    public int getNumberOfRooms()
    {
        return numberOfRooms;
    }

    public void setNumberOfRooms( int numberOfRooms )
    {
        this.numberOfRooms = numberOfRooms;
    }

    public int getNumberOfAdults()
    {
        return numberOfAdults;
    }

    public void setNumberOfAdults( int numberOfAdults )
    {
        this.numberOfAdults = numberOfAdults;
    }

    private int numberOfRooms;
    private int numberOfAdults;

    // Getters and Setters
}
