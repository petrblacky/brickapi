package com.blacky.brickapi.repository;

import com.blacky.brickapi.entity.LegoInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LegoInventoryRepository extends JpaRepository<LegoInventory,Long> {
    List<LegoInventory> findAllByLegoSet_SetNumberStartsWith(String id);
    @Query("""
    SELECT DISTINCT i FROM LegoInventory i
    LEFT JOIN FETCH i.minifigures im
    LEFT JOIN FETCH im.minifigure
    WHERE i.legoSet.setNumber = :setNumber
    """)
    List<LegoInventory> findAllWithMinifiguresBySetNumber(@Param("setNumber") String setNumber);
}
