package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class ConsentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsentDTO.class);
        ConsentDTO consentDTO1 = new ConsentDTO();
        consentDTO1.setId(1L);
        ConsentDTO consentDTO2 = new ConsentDTO();
        assertThat(consentDTO1).isNotEqualTo(consentDTO2);
        consentDTO2.setId(consentDTO1.getId());
        assertThat(consentDTO1).isEqualTo(consentDTO2);
        consentDTO2.setId(2L);
        assertThat(consentDTO1).isNotEqualTo(consentDTO2);
        consentDTO1.setId(null);
        assertThat(consentDTO1).isNotEqualTo(consentDTO2);
    }
}
