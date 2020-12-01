package com.amc.careplanner.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class CarerClientRelationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarerClientRelation.class);
        CarerClientRelation carerClientRelation1 = new CarerClientRelation();
        carerClientRelation1.setId(1L);
        CarerClientRelation carerClientRelation2 = new CarerClientRelation();
        carerClientRelation2.setId(carerClientRelation1.getId());
        assertThat(carerClientRelation1).isEqualTo(carerClientRelation2);
        carerClientRelation2.setId(2L);
        assertThat(carerClientRelation1).isNotEqualTo(carerClientRelation2);
        carerClientRelation1.setId(null);
        assertThat(carerClientRelation1).isNotEqualTo(carerClientRelation2);
    }
}
