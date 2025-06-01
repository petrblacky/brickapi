package com.blacky.brickapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "minifigs", schema = "brick")
public class LegoMinifigure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fig_num", nullable = false)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "num_parts")
    private Long partsCount;

    @Column(name = "img_url")
    private String imageUrl;
}
