package com.suntravels.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a request for room booking, including the number of rooms and adults.
 */
public class RoomRequest {

    /**
     * The number of rooms requested.
     */
    private int numberOfRooms;

    /**
     * The number of adults for the requested rooms.
     */
    private int numberOfAdults;

    /**
     * Default constructor for creating an empty {@link RoomRequest} instance.
     */
    public RoomRequest() {
    }

    /**
     * Constructor for creating a {@link RoomRequest} instance with specified parameters.
     *
     * @param numberOfRooms the number of rooms requested
     * @param numberOfAdults the number of adults for the request
     */
    public RoomRequest(int numberOfRooms, int numberOfAdults) {
        this.numberOfRooms = numberOfRooms;
        this.numberOfAdults = numberOfAdults;
    }

    /**
     * Gets the number of rooms requested.
     *
     * @return the number of rooms
     */
    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    /**
     * Sets the number of rooms requested.
     *
     * @param numberOfRooms the number of rooms to set
     */
    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    /**
     * Gets the number of adults for the requested rooms.
     *
     * @return the number of adults
     */
    public int getNumberOfAdults() {
        return numberOfAdults;
    }

    /**
     * Sets the number of adults for the requested rooms.
     *
     * @param numberOfAdults the number of adults to set
     */
    public void setNumberOfAdults(int numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }
}
