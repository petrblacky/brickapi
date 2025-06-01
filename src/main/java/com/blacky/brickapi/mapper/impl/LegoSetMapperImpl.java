package com.blacky.brickapi.mapper.impl;

import com.blacky.brickapi.dto.LegoMinifigureDto;
import com.blacky.brickapi.dto.LegoSetDto;
import com.blacky.brickapi.dto.LegoThemeDto;
import com.blacky.brickapi.entity.*;
import com.blacky.brickapi.mapper.LegoSetMapper;
import com.blacky.brickapi.repository.LegoInventoryRepository;
import com.blacky.brickapi.repository.LegoThemeRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LegoSetMapperImpl implements LegoSetMapper {
    private final LegoThemeRepository legoThemeRepository;
    private final LegoInventoryRepository legoInventoryRepository;

    public LegoSetMapperImpl(LegoThemeRepository legoThemeRepository,
                             LegoInventoryRepository legoInventoryRepository){
        this.legoThemeRepository = legoThemeRepository;
        this.legoInventoryRepository = legoInventoryRepository;
    }
    @Override
    public LegoSetDto legoSetToLegoSetDto(LegoSet legoSet) {
        LegoSetDto dto = new LegoSetDto();
        if (legoSet != null) {
            dto.setSetNumber(legoSet.getSetNumber());
            dto.setYear(legoSet.getYear());
            dto.setImageUrl(legoSet.getImageUrl());
            dto.setNumberOfParts(legoSet.getNumberOfParts());
            dto.setName(legoSet.getName());
            LegoThemeDto themeDto = new LegoThemeDto();

            if (legoSet.getTheme().getParentId() != null) {
                LegoTheme theme = legoThemeRepository.findOneById(legoSet.getTheme().getParentId());
                themeDto.setMainThemeId(theme.getId());
                themeDto.setSubThemeId(legoSet.getTheme().getId());
                themeDto.setMainThemeName(theme.getName());
                themeDto.setSubThemeName(legoSet.getTheme().getName());
                dto.setTheme(themeDto);
            } else {
                themeDto.setMainThemeId(legoSet.getTheme().getId());
                themeDto.setMainThemeName(legoSet.getTheme().getName());
                dto.setTheme(themeDto);
            }

            if(legoSet.getInventories() != null) {


                List<LegoInventory> inventories = legoInventoryRepository.findAllWithMinifiguresBySetNumber(
                        legoSet.getSetNumber());
                List<LegoMinifigureDto> minifiguresDto = new ArrayList<>();

                for (LegoInventory inventory : inventories) {
                    for (LegoInventoryMinifigure inventoryMinifigure : inventory.getMinifigures()) {
                        LegoMinifigureDto minifigureDto = new LegoMinifigureDto();
                        minifigureDto.setMinifigureNumber(inventoryMinifigure.getMinifigure().getId());
                        minifigureDto.setName(inventoryMinifigure.getMinifigure().getName());
                        minifigureDto.setNumberOfParts(inventoryMinifigure.getMinifigure().getPartsCount());
                        minifigureDto.setImageUrl(inventoryMinifigure.getMinifigure().getImageUrl());

                        minifiguresDto.add(minifigureDto);
                    }
                }
                dto.setMinifigures(minifiguresDto);
            }

        }

        return dto;
    }

    @Override
    public List<LegoSetDto> legoSetsToLegoSetsDto(List<LegoSet> legoSets) {
        return null;
    }
}
