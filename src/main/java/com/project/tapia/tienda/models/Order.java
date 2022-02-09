package com.project.tapia.tienda.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "arrangement")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime currentDateTime;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @OneToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    private Shop shop;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private List<Product> products;

    public Order(Client client, Shop shop, List products){
        this.currentDateTime = LocalDateTime.now();
        this.client = client;
        this.shop = shop;
        this.products = products;

    }

}
