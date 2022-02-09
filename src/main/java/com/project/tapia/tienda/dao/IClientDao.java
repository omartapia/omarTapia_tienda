package com.project.tapia.tienda.dao;

import com.project.tapia.tienda.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientDao extends JpaRepository<Client, Long> {
}
