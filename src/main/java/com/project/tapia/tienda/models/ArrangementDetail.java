package com.project.tapia.tienda.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "arrangementDetail")
public class ArrangementDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double total;

    private int quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "arrangement_id")
    @JsonIgnore
    private Arrangement arrangement;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

}
