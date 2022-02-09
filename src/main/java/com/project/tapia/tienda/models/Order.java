package com.project.tapia.tienda.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "order")
public class Orders implements Serializable {

    @Id
    private long order;

    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    private Client client;

    @OneToMany
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    private Inventory inventory;
}
