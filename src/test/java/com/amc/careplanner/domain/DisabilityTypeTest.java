package com.amc.careplanner.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class DisabilityTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DisabilityType.class);
        DisabilityType disabilityType1 = new DisabilityType();
        disabilityType1.setId(1L);
        DisabilityType disabilityType2 = new DisabilityType();
        disabilityType2.setId(disabilityType1.getId());
        assertThat(disabilityType1).isEqualTo(disabilityType2);
        disabilityType2.setId(2L);
        assertThat(disabilityType1).isNotEqualTo(disabilityType2);
        disabilityType1.setId(null);
        assertThat(disabilityType1).isNotEqualTo(disabilityType2);
    }
}
