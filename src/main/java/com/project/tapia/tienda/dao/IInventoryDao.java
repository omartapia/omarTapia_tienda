package com.project.tapia.tienda.dao;

import com.project.tapia.tienda.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IInventoryDao extends JpaRepository<Inventory, Long> {

    @Query("select i from Inventory i where i.shop.id =:shop and i.product.id in(:products)")
    List<Inventory> findByShopAndProduct(@Param("shop") Long shop, @Param("products") List<Long> products);
}
