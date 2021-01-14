package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class EmployeeLocationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeLocationDTO.class);
        EmployeeLocationDTO employeeLocationDTO1 = new EmployeeLocationDTO();
        employeeLocationDTO1.setId(1L);
        EmployeeLocationDTO employeeLocationDTO2 = new EmployeeLocationDTO();
        assertThat(employeeLocationDTO1).isNotEqualTo(employeeLocationDTO2);
        employeeLocationDTO2.setId(employeeLocationDTO1.getId());
        assertThat(employeeLocationDTO1).isEqualTo(employeeLocationDTO2);
        employeeLocationDTO2.setId(2L);
        assertThat(employeeLocationDTO1).isNotEqualTo(employeeLocationDTO2);
        employeeLocationDTO1.setId(null);
        assertThat(employeeLocationDTO1).isNotEqualTo(employeeLocationDTO2);
    }
}
