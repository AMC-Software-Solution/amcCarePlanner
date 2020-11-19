package com.amc.careplanner.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class EmployeeHolidayTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeHoliday.class);
        EmployeeHoliday employeeHoliday1 = new EmployeeHoliday();
        employeeHoliday1.setId(1L);
        EmployeeHoliday employeeHoliday2 = new EmployeeHoliday();
        employeeHoliday2.setId(employeeHoliday1.getId());
        assertThat(employeeHoliday1).isEqualTo(employeeHoliday2);
        employeeHoliday2.setId(2L);
        assertThat(employeeHoliday1).isNotEqualTo(employeeHoliday2);
        employeeHoliday1.setId(null);
        assertThat(employeeHoliday1).isNotEqualTo(employeeHoliday2);
    }
}
