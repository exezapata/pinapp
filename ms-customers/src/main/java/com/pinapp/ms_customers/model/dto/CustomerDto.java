package com.pinapp.ms_customers.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("name")
    @NotEmpty(message = "Name cannot be empty")
    @Schema(description = "Customer Name", example = "John")
    private String name;

    @JsonProperty("last_name")
    @NotEmpty(message = "Last name cannot be empty")
    @Schema(description = "Customer LastName", example = "Doe")
    private String lastName;

    @JsonProperty("age")
    @NotNull(message = "Age cannot be null")
    @Schema(description = "Customer Age", example = "30")
    private Integer age;

    @JsonProperty("birth_date")
    @NotNull(message = "Date of birth cannot be null")
    @Schema(description = "Customer Date of Birth", example = "1994-05-21")
    private String birthDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("probable_death_date")
    @Schema(description = "The probable death date based on the customer's birth date and average life expectancy.",
            example = "2074-05-21",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String probableDeathDate;
}