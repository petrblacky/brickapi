package com.blacky.brickapi.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvestmentAnalysis {
    private double projectedValue;
    private double investmentRating;

    public InvestmentAnalysis(double projectedValue, double investmentRating) {
        this.projectedValue = projectedValue;
        this.investmentRating = investmentRating;
    }
}
