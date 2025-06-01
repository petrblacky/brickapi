package com.blacky.brickapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LegoMinifigureDto {
    private String minifigureNumber;
    private String name;
    private Long numberOfParts;
    private String imageUrl;
}
