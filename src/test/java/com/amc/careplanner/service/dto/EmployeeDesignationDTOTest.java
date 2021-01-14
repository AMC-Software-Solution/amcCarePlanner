package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class EmployeeDesignationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeDesignationDTO.class);
        EmployeeDesignationDTO employeeDesignationDTO1 = new EmployeeDesignationDTO();
        employeeDesignationDTO1.setId(1L);
        EmployeeDesignationDTO employeeDesignationDTO2 = new EmployeeDesignationDTO();
        assertThat(employeeDesignationDTO1).isNotEqualTo(employeeDesignationDTO2);
        employeeDesignationDTO2.setId(employeeDesignationDTO1.getId());
        assertThat(employeeDesignationDTO1).isEqualTo(employeeDesignationDTO2);
        employeeDesignationDTO2.setId(2L);
        assertThat(employeeDesignationDTO1).isNotEqualTo(employeeDesignationDTO2);
        employeeDesignationDTO1.setId(null);
        assertThat(employeeDesignationDTO1).isNotEqualTo(employeeDesignationDTO2);
    }
}
