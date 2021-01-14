package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class MedicalContactDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalContactDTO.class);
        MedicalContactDTO medicalContactDTO1 = new MedicalContactDTO();
        medicalContactDTO1.setId(1L);
        MedicalContactDTO medicalContactDTO2 = new MedicalContactDTO();
        assertThat(medicalContactDTO1).isNotEqualTo(medicalContactDTO2);
        medicalContactDTO2.setId(medicalContactDTO1.getId());
        assertThat(medicalContactDTO1).isEqualTo(medicalContactDTO2);
        medicalContactDTO2.setId(2L);
        assertThat(medicalContactDTO1).isNotEqualTo(medicalContactDTO2);
        medicalContactDTO1.setId(null);
        assertThat(medicalContactDTO1).isNotEqualTo(medicalContactDTO2);
    }
}
