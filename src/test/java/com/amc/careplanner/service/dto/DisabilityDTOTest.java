package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class DisabilityDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DisabilityDTO.class);
        DisabilityDTO disabilityDTO1 = new DisabilityDTO();
        disabilityDTO1.setId(1L);
        DisabilityDTO disabilityDTO2 = new DisabilityDTO();
        assertThat(disabilityDTO1).isNotEqualTo(disabilityDTO2);
        disabilityDTO2.setId(disabilityDTO1.getId());
        assertThat(disabilityDTO1).isEqualTo(disabilityDTO2);
        disabilityDTO2.setId(2L);
        assertThat(disabilityDTO1).isNotEqualTo(disabilityDTO2);
        disabilityDTO1.setId(null);
        assertThat(disabilityDTO1).isNotEqualTo(disabilityDTO2);
    }
}
