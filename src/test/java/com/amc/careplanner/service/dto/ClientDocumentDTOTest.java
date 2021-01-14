package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class ClientDocumentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientDocumentDTO.class);
        ClientDocumentDTO clientDocumentDTO1 = new ClientDocumentDTO();
        clientDocumentDTO1.setId(1L);
        ClientDocumentDTO clientDocumentDTO2 = new ClientDocumentDTO();
        assertThat(clientDocumentDTO1).isNotEqualTo(clientDocumentDTO2);
        clientDocumentDTO2.setId(clientDocumentDTO1.getId());
        assertThat(clientDocumentDTO1).isEqualTo(clientDocumentDTO2);
        clientDocumentDTO2.setId(2L);
        assertThat(clientDocumentDTO1).isNotEqualTo(clientDocumentDTO2);
        clientDocumentDTO1.setId(null);
        assertThat(clientDocumentDTO1).isNotEqualTo(clientDocumentDTO2);
    }
}
