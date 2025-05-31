package com.blacky.brickapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LegoSetDto {
    private String setNumber;
    private String name;
    private Long year;
    private Long numberOfParts;
    private String imageUrl;
    private LegoThemeDto theme;
}
