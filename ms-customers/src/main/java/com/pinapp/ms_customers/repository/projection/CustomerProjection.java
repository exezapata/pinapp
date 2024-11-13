package com.pinapp.ms_customers.repository.projection;

import java.util.Date;

public interface CustomerProjection {

    Long getId();
    String getName();
    String getLastname();
    Integer getAge();
    Date getBirthDate();
    String getProbableDeathDate();
}
