package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class EqualityDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EqualityDTO.class);
        EqualityDTO equalityDTO1 = new EqualityDTO();
        equalityDTO1.setId(1L);
        EqualityDTO equalityDTO2 = new EqualityDTO();
        assertThat(equalityDTO1).isNotEqualTo(equalityDTO2);
        equalityDTO2.setId(equalityDTO1.getId());
        assertThat(equalityDTO1).isEqualTo(equalityDTO2);
        equalityDTO2.setId(2L);
        assertThat(equalityDTO1).isNotEqualTo(equalityDTO2);
        equalityDTO1.setId(null);
        assertThat(equalityDTO1).isNotEqualTo(equalityDTO2);
    }
}
