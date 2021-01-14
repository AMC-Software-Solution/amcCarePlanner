package com.amc.careplanner.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class EmployeeAvailabilityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeAvailability.class);
        EmployeeAvailability employeeAvailability1 = new EmployeeAvailability();
        employeeAvailability1.setId(1L);
        EmployeeAvailability employeeAvailability2 = new EmployeeAvailability();
        employeeAvailability2.setId(employeeAvailability1.getId());
        assertThat(employeeAvailability1).isEqualTo(employeeAvailability2);
        employeeAvailability2.setId(2L);
        assertThat(employeeAvailability1).isNotEqualTo(employeeAvailability2);
        employeeAvailability1.setId(null);
        assertThat(employeeAvailability1).isNotEqualTo(employeeAvailability2);
    }
}
