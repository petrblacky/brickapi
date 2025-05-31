package com.blacky.brickapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
}
