package com.suntravels.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Represents a room type associated with a contract, including details like type name, price per person, number of rooms, and maximum number of adults.
 */
@Data
@AllArgsConstructor
@Entity
@Table(name = "RoomType")
public class RoomType {

    /**
     * Unique identifier for the room type.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_type_id", nullable = false, updatable = false)
    private Long roomTypeId;

    /**
     * Name of the room type (e.g., Deluxe, Suite).
     */
    @Column(name = "type_name", nullable = false, length = 50)
    private String typeName;

    /**
     * Price per person for the room type.
     * Must be a positive value.
     */
    @Column(name = "per_person_price", nullable = false, precision = 10, scale = 2)
    @Positive(message = "Price must be positive")
    private BigDecimal perPersonPrice;

    /**
     * Number of rooms available for this room type.
     */
    @Column(name = "no_of_rooms", nullable = false)
    private int noOfRooms;

    /**
     * Maximum number of adults allowed in this room type.
     */
    @Column(name = "max_no_of_adults", nullable = false)
    private int maxNoAdults;

    /**
     * The contract associated with this room type.
     */
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    /**
     * Default constructor for creating an empty {@link RoomType} instance.
     */
    public RoomType() {
    }

    /**
     * Constructor for creating a {@link RoomType} instance with specified parameters.
     *
     * @param roomTypeId     the unique identifier of the room type
     * @param typeName       the name of the room type
     * @param perPersonPrice the price per person for this room type
     * @param noOfRooms      the number of rooms available
     * @param contract       the associated contract
     * @param maxNoAdults    the maximum number of adults allowed
     */
    public RoomType(Long roomTypeId, String typeName, BigDecimal perPersonPrice, int noOfRooms, Contract contract, int maxNoAdults) {
        this.roomTypeId = roomTypeId;
        this.typeName = typeName;
        this.perPersonPrice = perPersonPrice;
        this.noOfRooms = noOfRooms;
        this.contract = contract;
        this.maxNoAdults = maxNoAdults;
    }

    /**
     * Gets the unique identifier for the room type.
     *
     * @return the room type ID
     */
    public Long getRoomTypeId() {
        return roomTypeId;
    }

    /**
     * Sets the unique identifier for the room type.
     *
     * @param roomTypeId the room type ID
     */
    public void setRoomTypeId(Long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    /**
     * Gets the name of the room type.
     *
     * @return the room type name
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * Sets the name of the room type.
     *
     * @param typeName the room type name
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * Gets the price per person for the room type.
     *
     * @return the price per person
     */
    public BigDecimal getPerPersonPrice() {
        return perPersonPrice;
    }

    /**
     * Sets the price per person for the room type.
     *
     * @param perPersonPrice the price per person
     */
    public void setPerPersonPrice(BigDecimal perPersonPrice) {
        this.perPersonPrice = perPersonPrice;
    }

    /**
     * Gets the number of rooms available for this room type.
     *
     * @return the number of rooms
     */
    public int getNoOfRooms() {
        return noOfRooms;
    }

    /**
     * Sets the number of rooms available for this room type.
     *
     * @param noOfRooms the number of rooms
     */
    public void setNoOfRooms(int noOfRooms) {
        this.noOfRooms = noOfRooms;
    }

    /**
     * Gets the maximum number of adults allowed in this room type.
     *
     * @return the maximum number of adults
     */
    public int getMaxNoAdults() {
        return maxNoAdults;
    }

    /**
     * Sets the maximum number of adults allowed in this room type.
     *
     * @param maxNoAdults the maximum number of adults
     */
    public void setMaxNoAdults(int maxNoAdults) {
        this.maxNoAdults = maxNoAdults;
    }

    /**
     * Gets the contract associated with this room type.
     *
     * @return the associated contract
     */
    public Contract getContract() {
        return contract;
    }

    /**
     * Sets the contract associated with this room type.
     *
     * @param contract the associated contract
     */
    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
