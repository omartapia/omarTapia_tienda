package com.project.tapia.tienda.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "arrangement")
public class Arrangement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    private LocalDateTime currentDateTime;

    @Transient
    private String currentDateTimes;


    private Double total;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @OneToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    private Shop shop;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, targetEntity = ArrangementDetail.class,
            mappedBy="arrangement", orphanRemoval = true)
    private List<ArrangementDetail> details;

    public Arrangement(){
        this.currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.currentDateTimes = this.currentDateTime.format(formatter);
    }

    public Arrangement(Client client, Shop shop, List details, Double total){
        this.currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.currentDateTimes = this.currentDateTime.format(formatter);
        this.client = client;
        this.shop = shop;
        this.details = details;
        this.total = total;
    }

}
