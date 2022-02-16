package com.project.tapia.tienda.models.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.tapia.tienda.configuration.Configuration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportTransactionARequest {

    @NotNull(message = "el campo es requerido")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Configuration.DATE_PATTERN)
    private LocalDate startDate;

    @NotNull(message = "el campo es requerido")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Configuration.DATE_PATTERN)
    private LocalDate endDate;

}
