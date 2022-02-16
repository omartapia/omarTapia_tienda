package com.project.tapia.tienda.models.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportTransactionAResponse {

    private String shop;

    private LocalDateTime localDateTime;

    private Long transactions;

    public ReportTransactionAResponse() {

    }

    public ReportTransactionAResponse(String shop, LocalDateTime localDateTime, Long transactions) {
        this.shop = shop;
        this.localDateTime = localDateTime.toLocalDate().atStartOfDay();
        this.transactions = transactions;
    }

}
