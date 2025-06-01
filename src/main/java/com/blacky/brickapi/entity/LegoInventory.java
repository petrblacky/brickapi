package com.blacky.brickapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "inventories", schema = "brick")
public class LegoInventory {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "version")
    private Long version;

    @ManyToOne
    @JoinColumn(name = "set_num")
    private LegoSet legoSet;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LegoInventoryMinifigure> minifigures = new ArrayList<>();
}
