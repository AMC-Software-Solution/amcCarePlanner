package com.amc.careplanner.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class MedicalContactTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalContact.class);
        MedicalContact medicalContact1 = new MedicalContact();
        medicalContact1.setId(1L);
        MedicalContact medicalContact2 = new MedicalContact();
        medicalContact2.setId(medicalContact1.getId());
        assertThat(medicalContact1).isEqualTo(medicalContact2);
        medicalContact2.setId(2L);
        assertThat(medicalContact1).isNotEqualTo(medicalContact2);
        medicalContact1.setId(null);
        assertThat(medicalContact1).isNotEqualTo(medicalContact2);
    }
}
