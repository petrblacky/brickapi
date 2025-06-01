package com.blacky.brickapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sets", schema = "brick")
public class LegoSet {
    @Id
    @Column(name = "set_num")
    private String setNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "year")
    private Long year;

    @ManyToOne
    private LegoTheme theme;

    @Column(name = "num_parts")
    private Long numberOfParts;

    @Column(name = "img_url")
    private String imageUrl;

    @Column(name = "price_min")
    private Double priceMin;

    @Column(name = "price_max")
    private Double priceAvg;

    @Column(name = "price_avg")
    private Double priceMax;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @OneToMany(mappedBy = "legoSet")
    private List<LegoInventory> inventories = new ArrayList<>();
}
