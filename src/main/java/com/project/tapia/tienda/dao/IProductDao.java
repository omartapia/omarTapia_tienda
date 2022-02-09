package com.project.tapia.tienda.dao;

import com.project.tapia.tienda.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductDao extends JpaRepository<Product, Long> {
}
