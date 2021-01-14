package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class EmployeeAvailabilityDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeAvailabilityDTO.class);
        EmployeeAvailabilityDTO employeeAvailabilityDTO1 = new EmployeeAvailabilityDTO();
        employeeAvailabilityDTO1.setId(1L);
        EmployeeAvailabilityDTO employeeAvailabilityDTO2 = new EmployeeAvailabilityDTO();
        assertThat(employeeAvailabilityDTO1).isNotEqualTo(employeeAvailabilityDTO2);
        employeeAvailabilityDTO2.setId(employeeAvailabilityDTO1.getId());
        assertThat(employeeAvailabilityDTO1).isEqualTo(employeeAvailabilityDTO2);
        employeeAvailabilityDTO2.setId(2L);
        assertThat(employeeAvailabilityDTO1).isNotEqualTo(employeeAvailabilityDTO2);
        employeeAvailabilityDTO1.setId(null);
        assertThat(employeeAvailabilityDTO1).isNotEqualTo(employeeAvailabilityDTO2);
    }
}
