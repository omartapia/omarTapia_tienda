package com.project.tapia.tienda.dao;

import com.project.tapia.tienda.models.OrderDetail;
import com.project.tapia.tienda.models.response.ReportTransactionBResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderDetailDao extends JpaRepository<OrderDetail, Long> {
    @Query("select o.shop.name as shop, p.name as product, sum(d.total) as total from OrderDetail d join Order o join Product p group by o.shop.name, p.name")
    List<ReportTransactionBResponse> findBTransactionReportB();
}
