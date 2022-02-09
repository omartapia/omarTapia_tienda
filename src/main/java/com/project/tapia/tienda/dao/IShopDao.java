package com.project.tapia.tienda.dao;

import com.project.tapia.tienda.models.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IShopDao extends JpaRepository<Shop, Long> {
}
