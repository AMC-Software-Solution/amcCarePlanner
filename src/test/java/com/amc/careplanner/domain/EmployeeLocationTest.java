package com.amc.careplanner.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class EmployeeLocationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeLocation.class);
        EmployeeLocation employeeLocation1 = new EmployeeLocation();
        employeeLocation1.setId(1L);
        EmployeeLocation employeeLocation2 = new EmployeeLocation();
        employeeLocation2.setId(employeeLocation1.getId());
        assertThat(employeeLocation1).isEqualTo(employeeLocation2);
        employeeLocation2.setId(2L);
        assertThat(employeeLocation1).isNotEqualTo(employeeLocation2);
        employeeLocation1.setId(null);
        assertThat(employeeLocation1).isNotEqualTo(employeeLocation2);
    }
}
