package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class ExtraDataDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExtraDataDTO.class);
        ExtraDataDTO extraDataDTO1 = new ExtraDataDTO();
        extraDataDTO1.setId(1L);
        ExtraDataDTO extraDataDTO2 = new ExtraDataDTO();
        assertThat(extraDataDTO1).isNotEqualTo(extraDataDTO2);
        extraDataDTO2.setId(extraDataDTO1.getId());
        assertThat(extraDataDTO1).isEqualTo(extraDataDTO2);
        extraDataDTO2.setId(2L);
        assertThat(extraDataDTO1).isNotEqualTo(extraDataDTO2);
        extraDataDTO1.setId(null);
        assertThat(extraDataDTO1).isNotEqualTo(extraDataDTO2);
    }
}
