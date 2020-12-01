package com.amc.careplanner.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class PowerOfAttorneyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PowerOfAttorney.class);
        PowerOfAttorney powerOfAttorney1 = new PowerOfAttorney();
        powerOfAttorney1.setId(1L);
        PowerOfAttorney powerOfAttorney2 = new PowerOfAttorney();
        powerOfAttorney2.setId(powerOfAttorney1.getId());
        assertThat(powerOfAttorney1).isEqualTo(powerOfAttorney2);
        powerOfAttorney2.setId(2L);
        assertThat(powerOfAttorney1).isNotEqualTo(powerOfAttorney2);
        powerOfAttorney1.setId(null);
        assertThat(powerOfAttorney1).isNotEqualTo(powerOfAttorney2);
    }
}
