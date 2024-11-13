package com.pinapp.ms_customers.service;


import com.pinapp.ms_customers.model.dto.CustomerDto;
import com.pinapp.ms_customers.model.dto.KpiCustomerDto;

import java.util.List;

public interface CustomerService {

    CustomerDto createCustomer(CustomerDto customerDto);

    KpiCustomerDto getKpiCustomers();

    List<CustomerDto> getAllCustomersWithDeathDate();
}
