package com.suntravels.backend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) for representing room type information.
 * This class is used to transfer room type data between layers of the application.
 */
public class RoomTypeDto {

    /**
     * The unique identifier of the contract associated with the room type.
     */
    private Long contractId;

    /**
     * The name of the room type. Must not be blank.
     */
    @NotBlank(message = "Room type name must not be blank")
    private String typeName;

    /**
     * The price per person for the room. Must be greater than 0.
     */
    @NotNull(message = "Per person price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Per person price must be greater than 0")
    private BigDecimal perPersonPrice;

    /**
     * The number of rooms available for this room type. Must be greater than 0.
     */
    @Positive(message = "Number of rooms must be greater than 0")
    private int noOfRooms;

    /**
     * The maximum number of adults allowed in this room type. Must be greater than 0.
     */
    @Positive(message = "Maximum number of adults must be greater than 0")
    private int maxNoOfAdults;

    /**
     * Default constructor for creating an empty {@link RoomTypeDto} instance.
     */
    public RoomTypeDto() {
    }

    /**
     * Constructor for creating a {@link RoomTypeDto} instance with the specified parameters.
     *
     * @param contractId     the unique identifier of the contract associated with the room type
     * @param typeName       the name of the room type
     * @param perPersonPrice the price per person for the room
     * @param noOfRooms      the number of rooms available for this room type
     * @param maxNoOfAdults  the maximum number of adults allowed in this room type
     */
    public RoomTypeDto(Long contractId, String typeName, BigDecimal perPersonPrice, int noOfRooms, int maxNoOfAdults) {
        this.contractId = contractId;
        this.typeName = typeName;
        this.perPersonPrice = perPersonPrice;
        this.noOfRooms = noOfRooms;
        this.maxNoOfAdults = maxNoOfAdults;
    }

    /**
     * Gets the unique identifier of the contract associated with the room type.
     *
     * @return the contract ID
     */
    public Long getContractId() {
        return contractId;
    }

    /**
     * Sets the unique identifier of the contract associated with the room type.
     *
     * @param contractId the contract ID to set
     */
    public void setContractId(Long contractId) {
        this.contractId = contractId;
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
     * @param typeName the room type name to set
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
     * @param perPersonPrice the price per person to set
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
     * @param noOfRooms the number of rooms to set
     */
    public void setNoOfRooms(int noOfRooms) {
        this.noOfRooms = noOfRooms;
    }

    /**
     * Gets the maximum number of adults allowed in this room type.
     *
     * @return the maximum number of adults
     */
    public int getMaxNoOfAdults() {
        return maxNoOfAdults;
    }

    /**
     * Sets the maximum number of adults allowed in this room type.
     *
     * @param maxNoOfAdults the maximum number of adults to set
     */
    public void setMaxNoOfAdults(int maxNoOfAdults) {
        this.maxNoOfAdults = maxNoOfAdults;
    }
}
