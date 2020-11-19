package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class PowerOfAttorneyDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PowerOfAttorneyDTO.class);
        PowerOfAttorneyDTO powerOfAttorneyDTO1 = new PowerOfAttorneyDTO();
        powerOfAttorneyDTO1.setId(1L);
        PowerOfAttorneyDTO powerOfAttorneyDTO2 = new PowerOfAttorneyDTO();
        assertThat(powerOfAttorneyDTO1).isNotEqualTo(powerOfAttorneyDTO2);
        powerOfAttorneyDTO2.setId(powerOfAttorneyDTO1.getId());
        assertThat(powerOfAttorneyDTO1).isEqualTo(powerOfAttorneyDTO2);
        powerOfAttorneyDTO2.setId(2L);
        assertThat(powerOfAttorneyDTO1).isNotEqualTo(powerOfAttorneyDTO2);
        powerOfAttorneyDTO1.setId(null);
        assertThat(powerOfAttorneyDTO1).isNotEqualTo(powerOfAttorneyDTO2);
    }
}
