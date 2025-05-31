package com.blacky.brickapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LegoThemeDto {
    private Long mainThemeId;
    private String mainThemeName;
    private Long subThemeId;
    private String subThemeName;
}
