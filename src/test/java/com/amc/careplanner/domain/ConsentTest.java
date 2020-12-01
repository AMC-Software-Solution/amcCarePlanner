package com.amc.careplanner.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class ConsentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Consent.class);
        Consent consent1 = new Consent();
        consent1.setId(1L);
        Consent consent2 = new Consent();
        consent2.setId(consent1.getId());
        assertThat(consent1).isEqualTo(consent2);
        consent2.setId(2L);
        assertThat(consent1).isNotEqualTo(consent2);
        consent1.setId(null);
        assertThat(consent1).isNotEqualTo(consent2);
    }
}
