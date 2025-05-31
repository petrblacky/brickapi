package com.blacky.brickapi.repository;

import com.blacky.brickapi.dto.LegoSetDto;
import com.blacky.brickapi.entity.LegoInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LegoInventoryRepository extends JpaRepository<LegoInventory,Long> {
    List<LegoInventory> findAllByLegoSet_SetNumberStartsWith(String id);
}
