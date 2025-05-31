package com.blacky.brickapi.repository;

import com.blacky.brickapi.entity.LegoTheme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegoThemeRepository extends JpaRepository<LegoTheme,Long> {
    LegoTheme findOneById(Long id);
}
