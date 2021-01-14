package com.amc.careplanner.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class ExtraDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExtraData.class);
        ExtraData extraData1 = new ExtraData();
        extraData1.setId(1L);
        ExtraData extraData2 = new ExtraData();
        extraData2.setId(extraData1.getId());
        assertThat(extraData1).isEqualTo(extraData2);
        extraData2.setId(2L);
        assertThat(extraData1).isNotEqualTo(extraData2);
        extraData1.setId(null);
        assertThat(extraData1).isNotEqualTo(extraData2);
    }
}
