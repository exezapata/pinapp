package com.pinapp.ms_customers.service;

import com.pinapp.ms_customers.model.Customer;
import com.pinapp.ms_customers.model.dto.CustomerDto;
import com.pinapp.ms_customers.model.dto.KpiCustomerDto;
import com.pinapp.ms_customers.repository.CustomerRespository;
import com.pinapp.ms_customers.repository.projection.CustomerProjection;
import com.pinapp.ms_customers.repository.projection.KpiCustomerProjection;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRespository customerRespository;

    private final ModelMapper modelMapper;

    public CustomerServiceImpl(@Autowired CustomerRespository customerRespository, @Autowired ModelMapper modelMapper) {
        this.customerRespository = customerRespository;
        this.modelMapper = modelMapper;
    }

    @Value("${life.expectancy}")
    private int lifeExpectancy;

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto){
        Customer newCustomer = modelMapper.map(customerDto, Customer.class);
        return modelMapper.map(customerRespository.save(newCustomer), CustomerDto.class);
    }

    @Override
    public KpiCustomerDto getKpiCustomers() {

        log.info("Fetching customer KPIs...");

        KpiCustomerProjection kpiProjection = this.customerRespository.getKpiCustomers();

        if (kpiProjection == null) {
            log.info("No customers found for KPI calculation.");

            return KpiCustomerDto.builder().build();
        } else {

            double averageAge = BigDecimal.valueOf(kpiProjection.getAverageAge())
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
            double standardDeviationAge = BigDecimal.valueOf(kpiProjection.getStandardDeviationAge())
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();

            log.info(String.format("KPI calculated - Average Age: %.2f, Standard Deviation: %.2f",
                    averageAge, standardDeviationAge));

            return KpiCustomerDto.builder()
                    .averageAge(averageAge)
                    .standardDeviationAge(standardDeviationAge)
                    .build();
        }
    }

    @Override
    public List<CustomerDto> getAllCustomersWithDeathDate() {

        List<CustomerProjection> projections = this.customerRespository.getAllCustomersWithDeathDate(lifeExpectancy);

        List<CustomerDto> response = new ArrayList<>();

        for (CustomerProjection projection : projections) {

            String probableDeathDate = projection.getProbableDeathDate();

            CustomerDto customerDto = CustomerDto.builder()
                    .name(projection.getName())
                    .lastName(projection.getLastname())
                    .age(projection.getAge())
                    .birthDate(new SimpleDateFormat("yyyy-MM-dd").format(projection.getBirthDate()))
                    .probableDeathDate(probableDeathDate)
                    .build();

            response.add(customerDto);
        }

        log.info(String.format("Fetched %d customers with death dates.", response.size()));

        return response;
    }

}
