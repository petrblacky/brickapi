package com.blacky.brickapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "inventory_minifigs", schema = "brick")
public class LegoInventoryMinifigure {
    @EmbeddedId
    private LegoInventoryMinifigureId id;

    @ManyToOne
    @MapsId("inventoryId")
    @JoinColumn(name = "inventory_id", nullable = false)
    private LegoInventory inventory;

    @ManyToOne
    @MapsId("figNum")
    @JoinColumn(name = "fig_num", referencedColumnName = "fig_num")
    private LegoMinifigure minifigure;

    @Column(name = "quantity")
    private Long quantity;
}
