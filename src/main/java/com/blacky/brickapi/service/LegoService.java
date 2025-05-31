package com.blacky.brickapi.service;

import com.blacky.brickapi.dto.LegoSetDto;
import com.blacky.brickapi.dto.LegoSetValuesDto;

import java.io.IOException;
import java.util.List;

public interface LegoService {

    /**
     * Get Lego set by ID.
     * @param id Lego set ID
     */
    List<LegoSetDto> getLegoSets(String id);

    /**
     * Get all retiring lego sets from lego.com.
     */
    List<LegoSetValuesDto> scrapRetiringSets();
}
