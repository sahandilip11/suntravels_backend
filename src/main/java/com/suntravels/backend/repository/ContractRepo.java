package com.suntravels.backend.repository;

import com.suntravels.backend.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations on {@link Contract} entities.
 *
 * <p>This interface extends the {@link JpaRepository}, providing methods for
 * interacting with the database such as saving, deleting, and finding {@link Contract} entities.</p>
 *
 * <p>The repository is annotated with {@link Repository}, making it a Spring-managed component.</p>
 */
@Repository
public interface ContractRepo extends JpaRepository<Contract, Long> {
    // No additional methods are needed as JpaRepository provides the standard CRUD operations.
}
