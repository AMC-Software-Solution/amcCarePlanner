package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class DisabilityTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DisabilityTypeDTO.class);
        DisabilityTypeDTO disabilityTypeDTO1 = new DisabilityTypeDTO();
        disabilityTypeDTO1.setId(1L);
        DisabilityTypeDTO disabilityTypeDTO2 = new DisabilityTypeDTO();
        assertThat(disabilityTypeDTO1).isNotEqualTo(disabilityTypeDTO2);
        disabilityTypeDTO2.setId(disabilityTypeDTO1.getId());
        assertThat(disabilityTypeDTO1).isEqualTo(disabilityTypeDTO2);
        disabilityTypeDTO2.setId(2L);
        assertThat(disabilityTypeDTO1).isNotEqualTo(disabilityTypeDTO2);
        disabilityTypeDTO1.setId(null);
        assertThat(disabilityTypeDTO1).isNotEqualTo(disabilityTypeDTO2);
    }
}
