package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class AccessDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccessDTO.class);
        AccessDTO accessDTO1 = new AccessDTO();
        accessDTO1.setId(1L);
        AccessDTO accessDTO2 = new AccessDTO();
        assertThat(accessDTO1).isNotEqualTo(accessDTO2);
        accessDTO2.setId(accessDTO1.getId());
        assertThat(accessDTO1).isEqualTo(accessDTO2);
        accessDTO2.setId(2L);
        assertThat(accessDTO1).isNotEqualTo(accessDTO2);
        accessDTO1.setId(null);
        assertThat(accessDTO1).isNotEqualTo(accessDTO2);
    }
}
