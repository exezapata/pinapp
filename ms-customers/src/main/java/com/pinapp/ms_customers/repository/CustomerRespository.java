package com.pinapp.ms_customers.repository;

import com.pinapp.ms_customers.model.Customer;
import com.pinapp.ms_customers.repository.projection.CustomerProjection;
import com.pinapp.ms_customers.repository.projection.KpiCustomerProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRespository extends JpaRepository<Customer, Long> {

    @Query(value = " SELECT " +
            "   c.id as id, " +
            "   c.name as name, " +
            "   c.lastname as lastName, " +
            "   c.age as age, " +
            "   c.birth_date as birthDate, " +
            "   DATE_ADD(c.birth_date, INTERVAL :years YEAR) AS probableDeathDate " +
            " FROM customer c ", nativeQuery = true)
    List<CustomerProjection> getAllCustomersWithDeathDate(@Param("years") int years);

    @Query(value = " SELECT " +
            "   AVG(c.age) as averageAge, " +
            "   STDDEV_POP(c.age) as standardDeviationAge " +
            "   FROM customer c", nativeQuery = true)
    KpiCustomerProjection getKpiCustomers();
}
