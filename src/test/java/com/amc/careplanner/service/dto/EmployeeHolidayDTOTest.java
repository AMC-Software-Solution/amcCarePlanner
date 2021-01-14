package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class EmployeeHolidayDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeHolidayDTO.class);
        EmployeeHolidayDTO employeeHolidayDTO1 = new EmployeeHolidayDTO();
        employeeHolidayDTO1.setId(1L);
        EmployeeHolidayDTO employeeHolidayDTO2 = new EmployeeHolidayDTO();
        assertThat(employeeHolidayDTO1).isNotEqualTo(employeeHolidayDTO2);
        employeeHolidayDTO2.setId(employeeHolidayDTO1.getId());
        assertThat(employeeHolidayDTO1).isEqualTo(employeeHolidayDTO2);
        employeeHolidayDTO2.setId(2L);
        assertThat(employeeHolidayDTO1).isNotEqualTo(employeeHolidayDTO2);
        employeeHolidayDTO1.setId(null);
        assertThat(employeeHolidayDTO1).isNotEqualTo(employeeHolidayDTO2);
    }
}
