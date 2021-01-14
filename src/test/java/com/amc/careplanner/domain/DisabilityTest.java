package com.amc.careplanner.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class DisabilityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Disability.class);
        Disability disability1 = new Disability();
        disability1.setId(1L);
        Disability disability2 = new Disability();
        disability2.setId(disability1.getId());
        assertThat(disability1).isEqualTo(disability2);
        disability2.setId(2L);
        assertThat(disability1).isNotEqualTo(disability2);
        disability1.setId(null);
        assertThat(disability1).isNotEqualTo(disability2);
    }
}
