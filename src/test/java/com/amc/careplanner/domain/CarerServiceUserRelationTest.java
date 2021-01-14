package com.amc.careplanner.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class CarerServiceUserRelationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarerServiceUserRelation.class);
        CarerServiceUserRelation carerServiceUserRelation1 = new CarerServiceUserRelation();
        carerServiceUserRelation1.setId(1L);
        CarerServiceUserRelation carerServiceUserRelation2 = new CarerServiceUserRelation();
        carerServiceUserRelation2.setId(carerServiceUserRelation1.getId());
        assertThat(carerServiceUserRelation1).isEqualTo(carerServiceUserRelation2);
        carerServiceUserRelation2.setId(2L);
        assertThat(carerServiceUserRelation1).isNotEqualTo(carerServiceUserRelation2);
        carerServiceUserRelation1.setId(null);
        assertThat(carerServiceUserRelation1).isNotEqualTo(carerServiceUserRelation2);
    }
}
