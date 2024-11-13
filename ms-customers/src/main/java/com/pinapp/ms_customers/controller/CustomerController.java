package com.pinapp.ms_customers.controller;


import com.pinapp.ms_customers.model.dto.CustomerDto;
import com.pinapp.ms_customers.model.dto.KpiCustomerDto;
import com.pinapp.ms_customers.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(@Autowired CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(
            summary = "Create a new customer",
            description = "This endpoint creates a customer with the information provided",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Customer created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping("/create-customer")
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        CustomerDto createdCustomer = customerService.createCustomer(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }


    @Operation(
            summary = "Get Customer KPIs",
            description = "This endpoint returns the average age and standard deviation of the ages of all customers.",
            tags = {"Customers"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer KPI successfully achieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = KpiCustomerDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/kpi-customers")
    public KpiCustomerDto getKpiCustomers() {
        return this.customerService.getKpiCustomers();
    }


    @Operation(
            summary = "Get list of customers with probable death date",
            description = "This endpoint returns a list of all customers with their personal information and their probable death date, calculated based on a life expectancy of 80 years.",
            tags = {"Customers"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of customers",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/list-customers")
    public List<CustomerDto> getListCustomers() {
        return customerService.getAllCustomersWithDeathDate();
    }
}
