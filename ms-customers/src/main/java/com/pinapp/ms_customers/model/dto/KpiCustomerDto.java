package com.pinapp.ms_customers.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KpiCustomerDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("average_age")
    private double averageAge;

    @JsonProperty("standard_deviation_age")
    private double standardDeviationAge;
}