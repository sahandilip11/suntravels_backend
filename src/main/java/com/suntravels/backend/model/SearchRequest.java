package com.suntravels.backend.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a search request for hotel room availability.
 * Contains details such as check-in date, number of nights, and room requirements.
 */
public class SearchRequest {

    /**
     * The check-in date for the search.
     */
    @NotNull(message = "Check-in date is required")
    @FutureOrPresent(message = "Check-in date must be today or in the future")
    private LocalDate checkInDate;

    /**
     * The number of nights for the stay.
     */
    @NotNull(message = "Number of nights is required")
    @Positive(message = "Number of nights must be greater than 0")
    private Integer numberOfNights;

    /**
     * A list of room requests specifying the number of rooms and adults.
     */
    @NotNull(message = "Room requests cannot be null")
    @Size(min = 1, message = "At least one room request is required")
    private List<@Valid RoomRequest> roomRequests;


    /**
     * Default constructor for creating an empty {@link SearchRequest} instance.
     */
    public SearchRequest() {
    }

    /**
     * Constructs a {@link SearchRequest} with the specified details.
     *
     * @param checkInDate   the date of check-in
     * @param numberOfNights the number of nights for the stay
     * @param roomRequests  the list of room requests
     */
    public SearchRequest(LocalDate checkInDate, int numberOfNights, List<RoomRequest> roomRequests) {
        this.checkInDate = checkInDate;
        this.numberOfNights = numberOfNights;
        this.roomRequests = roomRequests;
    }

    /**
     * Gets the check-in date for the search.
     *
     * @return the check-in date
     */
    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    /**
     * Sets the check-in date for the search.
     *
     * @param checkInDate the check-in date
     */
    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    /**
     * Gets the number of nights for the stay.
     *
     * @return the number of nights
     */
    public int getNumberOfNights() {
        return numberOfNights;
    }

    /**
     * Sets the number of nights for the stay.
     *
     * @param numberOfNights the number of nights
     */
    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    /**
     * Gets the list of room requests specifying the number of rooms and adults.
     *
     * @return the list of room requests
     */
    public List<RoomRequest> getRoomRequests() {
        return roomRequests;
    }

    /**
     * Sets the list of room requests specifying the number of rooms and adults.
     *
     * @param roomRequests the list of room requests
     */
    public void setRoomRequests(List<RoomRequest> roomRequests) {
        this.roomRequests = roomRequests;
    }
}
