package com.suntravels.backend.repository;

import com.suntravels.backend.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContractRepo extends JpaRepository<Contract, Long> {

}