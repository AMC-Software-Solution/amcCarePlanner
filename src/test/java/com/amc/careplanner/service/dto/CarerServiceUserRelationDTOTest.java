package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class CarerServiceUserRelationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarerServiceUserRelationDTO.class);
        CarerServiceUserRelationDTO carerServiceUserRelationDTO1 = new CarerServiceUserRelationDTO();
        carerServiceUserRelationDTO1.setId(1L);
        CarerServiceUserRelationDTO carerServiceUserRelationDTO2 = new CarerServiceUserRelationDTO();
        assertThat(carerServiceUserRelationDTO1).isNotEqualTo(carerServiceUserRelationDTO2);
        carerServiceUserRelationDTO2.setId(carerServiceUserRelationDTO1.getId());
        assertThat(carerServiceUserRelationDTO1).isEqualTo(carerServiceUserRelationDTO2);
        carerServiceUserRelationDTO2.setId(2L);
        assertThat(carerServiceUserRelationDTO1).isNotEqualTo(carerServiceUserRelationDTO2);
        carerServiceUserRelationDTO1.setId(null);
        assertThat(carerServiceUserRelationDTO1).isNotEqualTo(carerServiceUserRelationDTO2);
    }
}
