package com.blacky.brickapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LegoSetPredictionsDto {
    private String retirementPop;
    private String retirement;
    private String firstYearPrediction;
    private String secondYearPrediction;
    private String fiveYearsPrediction;
    private Double yearlyGrowthPercentagePrediction;
}
