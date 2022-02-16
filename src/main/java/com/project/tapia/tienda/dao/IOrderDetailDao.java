package com.project.tapia.tienda.dao;

import com.project.tapia.tienda.models.ArrangementDetail;
import com.project.tapia.tienda.models.response.ReportTransactionBResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderDetailDao extends JpaRepository<ArrangementDetail, Long> {
    @Query("select new com.project.tapia.tienda.models.response.ReportTransactionBResponse(s.name, p.name, sum(d.total)) from ArrangementDetail d join d.arrangement o join o.shop s join d.product p group by s.name, p.name")
    List<ReportTransactionBResponse> findBTransactionReportB();
}
