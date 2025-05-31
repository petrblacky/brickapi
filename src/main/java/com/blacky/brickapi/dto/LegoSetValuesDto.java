package com.blacky.brickapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LegoSetValuesDto {
    private String setNumber;
    private String investmentText;
    private Long buyOpportunityPoints;
    private Double price;
    private Double salePrice;
    private Double retailPrice;
    private LegoSetDto legoSetDto;
    private LegoSetPredictionsDto legoSetPredictionsDto;
}
