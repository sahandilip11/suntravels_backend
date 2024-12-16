package com.suntravels.backend.repository;

import com.suntravels.backend.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations on {@link RoomType} entities.
 *
 * <p>This interface extends the {@link JpaRepository}, providing methods for
 * interacting with the database such as saving, deleting, and finding {@link RoomType} entities.</p>
 *
 * <p>The repository is annotated with {@link Repository}, making it a Spring-managed component.</p>
 */
@Repository
public interface RoomTypeRepo extends JpaRepository<RoomType, Long> {

}
