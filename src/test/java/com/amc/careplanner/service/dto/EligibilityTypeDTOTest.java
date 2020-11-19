package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class EligibilityTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EligibilityTypeDTO.class);
        EligibilityTypeDTO eligibilityTypeDTO1 = new EligibilityTypeDTO();
        eligibilityTypeDTO1.setId(1L);
        EligibilityTypeDTO eligibilityTypeDTO2 = new EligibilityTypeDTO();
        assertThat(eligibilityTypeDTO1).isNotEqualTo(eligibilityTypeDTO2);
        eligibilityTypeDTO2.setId(eligibilityTypeDTO1.getId());
        assertThat(eligibilityTypeDTO1).isEqualTo(eligibilityTypeDTO2);
        eligibilityTypeDTO2.setId(2L);
        assertThat(eligibilityTypeDTO1).isNotEqualTo(eligibilityTypeDTO2);
        eligibilityTypeDTO1.setId(null);
        assertThat(eligibilityTypeDTO1).isNotEqualTo(eligibilityTypeDTO2);
    }
}
