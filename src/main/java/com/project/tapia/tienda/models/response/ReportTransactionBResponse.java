package com.project.tapia.tienda.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportTransactionBResponse {

    private String shop;

    private String product;

    private Double total;
}
