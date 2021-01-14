package com.amc.careplanner.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class EqualityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Equality.class);
        Equality equality1 = new Equality();
        equality1.setId(1L);
        Equality equality2 = new Equality();
        equality2.setId(equality1.getId());
        assertThat(equality1).isEqualTo(equality2);
        equality2.setId(2L);
        assertThat(equality1).isNotEqualTo(equality2);
        equality1.setId(null);
        assertThat(equality1).isNotEqualTo(equality2);
    }
}
