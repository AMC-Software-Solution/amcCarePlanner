package com.amc.careplanner.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class AccessTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Access.class);
        Access access1 = new Access();
        access1.setId(1L);
        Access access2 = new Access();
        access2.setId(access1.getId());
        assertThat(access1).isEqualTo(access2);
        access2.setId(2L);
        assertThat(access1).isNotEqualTo(access2);
        access1.setId(null);
        assertThat(access1).isNotEqualTo(access2);
    }
}
