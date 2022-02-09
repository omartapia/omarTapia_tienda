package com.project.tapia.tienda.dao;

import com.project.tapia.tienda.models.Order;
import com.project.tapia.tienda.models.response.ReportTransactionAResponse;
import com.project.tapia.tienda.models.response.ReportTransactionBResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderDao extends JpaRepository<Order, Long> {

    @Query("select o.shop.name as shop, o.currentDateTime as localDateTime, count(o.id) as transactions from Order o group by o.shop.name, o.currentDateTime")
    List<ReportTransactionAResponse> findBTransactionReportA();

    @Query("select o.shop.name as shop, p.name as product, sum(p.price) as total from Order o join Product p group by o.shop.name, p.name")
    List<ReportTransactionBResponse> findBTransactionReportB();
}
