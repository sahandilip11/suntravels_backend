package com.suntravels.backend.repository;

import com.suntravels.backend.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepo extends JpaRepository<RoomType,Long>
{

}
