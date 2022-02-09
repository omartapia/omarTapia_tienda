package com.project.tapia.tienda.dao;

import com.project.tapia.tienda.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientDao extends JpaRepository<Client, Long> {

    @Query("select c from Client c where c.identificacion =:identificacion")
    Client findClientByIdentification(@Param("identificacion") String identificacion);
}
