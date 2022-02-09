package com.project.tapia.tienda.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportTransactionAResponse {

    private String shop;

    private LocalDateTime localDateTime;

    private Integer transactions;
}
