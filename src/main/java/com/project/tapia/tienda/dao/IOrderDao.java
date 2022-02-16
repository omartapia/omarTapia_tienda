package com.project.tapia.tienda.dao;

import com.project.tapia.tienda.models.Arrangement;
import com.project.tapia.tienda.models.response.ReportTransactionAResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IOrderDao extends JpaRepository<Arrangement, Long> {

    @Query(value = "select new com.project.tapia.tienda.models.response.ReportTransactionAResponse(o.shop.name, o.currentDateTime, count(o.id)) from Arrangement o join o.shop s where o.currentDateTime between :sDate and :eDate group by s.name, o.currentDateTime")
    List<ReportTransactionAResponse> findBTransactionReportA(@Param(value = "sDate") LocalDateTime sDate, @Param(value = "eDate") LocalDateTime emDate);
}
