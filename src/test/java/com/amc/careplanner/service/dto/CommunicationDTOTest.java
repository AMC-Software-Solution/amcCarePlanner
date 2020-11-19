package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class CommunicationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommunicationDTO.class);
        CommunicationDTO communicationDTO1 = new CommunicationDTO();
        communicationDTO1.setId(1L);
        CommunicationDTO communicationDTO2 = new CommunicationDTO();
        assertThat(communicationDTO1).isNotEqualTo(communicationDTO2);
        communicationDTO2.setId(communicationDTO1.getId());
        assertThat(communicationDTO1).isEqualTo(communicationDTO2);
        communicationDTO2.setId(2L);
        assertThat(communicationDTO1).isNotEqualTo(communicationDTO2);
        communicationDTO1.setId(null);
        assertThat(communicationDTO1).isNotEqualTo(communicationDTO2);
    }
}
