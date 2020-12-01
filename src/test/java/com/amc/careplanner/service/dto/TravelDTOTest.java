package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class TravelDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TravelDTO.class);
        TravelDTO travelDTO1 = new TravelDTO();
        travelDTO1.setId(1L);
        TravelDTO travelDTO2 = new TravelDTO();
        assertThat(travelDTO1).isNotEqualTo(travelDTO2);
        travelDTO2.setId(travelDTO1.getId());
        assertThat(travelDTO1).isEqualTo(travelDTO2);
        travelDTO2.setId(2L);
        assertThat(travelDTO1).isNotEqualTo(travelDTO2);
        travelDTO1.setId(null);
        assertThat(travelDTO1).isNotEqualTo(travelDTO2);
    }
}
