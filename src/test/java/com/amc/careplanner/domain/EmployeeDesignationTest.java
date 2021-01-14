package com.amc.careplanner.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class EmployeeDesignationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeDesignation.class);
        EmployeeDesignation employeeDesignation1 = new EmployeeDesignation();
        employeeDesignation1.setId(1L);
        EmployeeDesignation employeeDesignation2 = new EmployeeDesignation();
        employeeDesignation2.setId(employeeDesignation1.getId());
        assertThat(employeeDesignation1).isEqualTo(employeeDesignation2);
        employeeDesignation2.setId(2L);
        assertThat(employeeDesignation1).isNotEqualTo(employeeDesignation2);
        employeeDesignation1.setId(null);
        assertThat(employeeDesignation1).isNotEqualTo(employeeDesignation2);
    }
}
