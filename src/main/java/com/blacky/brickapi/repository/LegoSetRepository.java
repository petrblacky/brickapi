package com.blacky.brickapi.repository;

import com.blacky.brickapi.entity.LegoSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegoSetRepository extends JpaRepository<LegoSet,String> {
    LegoSet findBySetNumberStartsWith(String setNumber);
}
