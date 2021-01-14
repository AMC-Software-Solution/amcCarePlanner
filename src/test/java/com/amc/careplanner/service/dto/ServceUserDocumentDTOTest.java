package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class ServceUserDocumentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServceUserDocumentDTO.class);
        ServceUserDocumentDTO servceUserDocumentDTO1 = new ServceUserDocumentDTO();
        servceUserDocumentDTO1.setId(1L);
        ServceUserDocumentDTO servceUserDocumentDTO2 = new ServceUserDocumentDTO();
        assertThat(servceUserDocumentDTO1).isNotEqualTo(servceUserDocumentDTO2);
        servceUserDocumentDTO2.setId(servceUserDocumentDTO1.getId());
        assertThat(servceUserDocumentDTO1).isEqualTo(servceUserDocumentDTO2);
        servceUserDocumentDTO2.setId(2L);
        assertThat(servceUserDocumentDTO1).isNotEqualTo(servceUserDocumentDTO2);
        servceUserDocumentDTO1.setId(null);
        assertThat(servceUserDocumentDTO1).isNotEqualTo(servceUserDocumentDTO2);
    }
}
