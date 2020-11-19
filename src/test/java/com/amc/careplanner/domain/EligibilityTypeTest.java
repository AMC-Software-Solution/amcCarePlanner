package com.amc.careplanner.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class EligibilityTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EligibilityType.class);
        EligibilityType eligibilityType1 = new EligibilityType();
        eligibilityType1.setId(1L);
        EligibilityType eligibilityType2 = new EligibilityType();
        eligibilityType2.setId(eligibilityType1.getId());
        assertThat(eligibilityType1).isEqualTo(eligibilityType2);
        eligibilityType2.setId(2L);
        assertThat(eligibilityType1).isNotEqualTo(eligibilityType2);
        eligibilityType1.setId(null);
        assertThat(eligibilityType1).isNotEqualTo(eligibilityType2);
    }
}
