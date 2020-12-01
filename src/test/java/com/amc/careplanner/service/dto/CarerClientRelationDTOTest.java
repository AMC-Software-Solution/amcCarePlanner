package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class CarerClientRelationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarerClientRelationDTO.class);
        CarerClientRelationDTO carerClientRelationDTO1 = new CarerClientRelationDTO();
        carerClientRelationDTO1.setId(1L);
        CarerClientRelationDTO carerClientRelationDTO2 = new CarerClientRelationDTO();
        assertThat(carerClientRelationDTO1).isNotEqualTo(carerClientRelationDTO2);
        carerClientRelationDTO2.setId(carerClientRelationDTO1.getId());
        assertThat(carerClientRelationDTO1).isEqualTo(carerClientRelationDTO2);
        carerClientRelationDTO2.setId(2L);
        assertThat(carerClientRelationDTO1).isNotEqualTo(carerClientRelationDTO2);
        carerClientRelationDTO1.setId(null);
        assertThat(carerClientRelationDTO1).isNotEqualTo(carerClientRelationDTO2);
    }
}
