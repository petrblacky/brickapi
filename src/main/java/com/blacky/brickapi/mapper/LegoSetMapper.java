package com.blacky.brickapi.mapper;

import com.blacky.brickapi.dto.LegoSetDto;
import com.blacky.brickapi.entity.LegoSet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface LegoSetMapper {
    LegoSetDto legoSetToLegoSetDto(LegoSet legoSet);

    List<LegoSetDto> legoSetsToLegoSetsDto(List<LegoSet> legoSets);
}
