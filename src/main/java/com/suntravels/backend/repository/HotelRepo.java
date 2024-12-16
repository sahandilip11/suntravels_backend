package com.suntravels.backend.repository;

import com.suntravels.backend.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on {@link Hotel} entities.
 *
 * <p>This interface extends the {@link JpaRepository}, providing methods for
 * interacting with the database such as saving, deleting, and finding {@link Hotel} entities.</p>
 *
 * <p>The repository is annotated with {@link Repository}, making it a Spring-managed component.</p>
 *
 * <p>Custom Query Methods:</p>
 * <ul>
 *   <li>{@code Optional<Hotel> findByHotelName(String hotelName)}:
 *   Retrieves a {@link Hotel} entity based on the hotel name.</li>
 * </ul>
 */
@Repository
public interface HotelRepo extends JpaRepository<Hotel, Long> {

    /**
     * Finds a {@link Hotel} by its name.
     *
     * @param hotelName the name of the hotel to search for.
     * @return an {@link Optional} containing the found {@link Hotel}, or empty if no match is found.
     */
    Optional<Hotel> findByHotelName(String hotelName);
}
