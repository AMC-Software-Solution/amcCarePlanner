package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class EmployeeDocumentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeDocumentDTO.class);
        EmployeeDocumentDTO employeeDocumentDTO1 = new EmployeeDocumentDTO();
        employeeDocumentDTO1.setId(1L);
        EmployeeDocumentDTO employeeDocumentDTO2 = new EmployeeDocumentDTO();
        assertThat(employeeDocumentDTO1).isNotEqualTo(employeeDocumentDTO2);
        employeeDocumentDTO2.setId(employeeDocumentDTO1.getId());
        assertThat(employeeDocumentDTO1).isEqualTo(employeeDocumentDTO2);
        employeeDocumentDTO2.setId(2L);
        assertThat(employeeDocumentDTO1).isNotEqualTo(employeeDocumentDTO2);
        employeeDocumentDTO1.setId(null);
        assertThat(employeeDocumentDTO1).isNotEqualTo(employeeDocumentDTO2);
    }
}
