package com.suntravels.backend.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents the result of a search for available rooms in a hotel.
 * Contains details about room types, pricing, availability, and the hotel's name.
 */
public class SearchResult {

    /**
     * The list of room types available for this search result.
     */
    private List<RoomType> roomType;

    /**
     * The total price for the available rooms.
     */
    private BigDecimal price;

    /**
     * The availability status of the hotel (e.g., Available, Unavailable).
     */
    private String availabilityStatus;

    /**
     * The name of the hotel associated with this search result.
     */
    private String hotelName;

    /**
     * Default constructor for creating an empty {@link SearchResult} instance.
     */
    public SearchResult() {
    }

    /**
     * Constructs a {@link SearchResult} with the specified details.
     *
     * @param roomType           the list of room types available
     * @param price              the total price for the available rooms
     * @param availabilityStatus the availability status of the hotel
     * @param hotelName          the name of the hotel
     */
    public SearchResult(List<RoomType> roomType, BigDecimal price, String availabilityStatus, String hotelName) {
        this.roomType = roomType;
        this.price = price;
        this.availabilityStatus = availabilityStatus;
        this.hotelName = hotelName;
    }

    /**
     * Gets the list of room types available for this search result.
     *
     * @return the list of room types
     */
    public List<RoomType> getRoomType() {
        return roomType;
    }

    /**
     * Sets the list of room types available for this search result.
     *
     * @param roomType the list of room types
     */
    public void setRoomType(List<RoomType> roomType) {
        this.roomType = roomType;
    }

    /**
     * Gets the total price for the available rooms.
     *
     * @return the total price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the total price for the available rooms.
     *
     * @param price the total price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets the availability status of the hotel.
     *
     * @return the availability status
     */
    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    /**
     * Sets the availability status of the hotel.
     *
     * @param availabilityStatus the availability status
     */
    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    /**
     * Gets the name of the hotel associated with this search result.
     *
     * @return the hotel name
     */
    public String getHotelName() {
        return hotelName;
    }

    /**
     * Sets the name of the hotel associated with this search result.
     *
     * @param hotelName the hotel name
     */
    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    /**
     * Provides a string representation of the {@link SearchResult}.
     *
     * @return a string representation of the search result
     */
    @Override
    public String toString() {
        return "SearchResult{" +
                       "roomType=" + roomType.stream().map(RoomType::toString).toList() +
                       ", price=" + price +
                       ", availabilityStatus='" + availabilityStatus + '\'' +
                       ", hotelName='" + hotelName + '\'' +
                       '}';
    }
}
