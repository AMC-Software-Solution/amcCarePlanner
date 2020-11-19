package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class TenantDocumentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TenantDocumentDTO.class);
        TenantDocumentDTO tenantDocumentDTO1 = new TenantDocumentDTO();
        tenantDocumentDTO1.setId(1L);
        TenantDocumentDTO tenantDocumentDTO2 = new TenantDocumentDTO();
        assertThat(tenantDocumentDTO1).isNotEqualTo(tenantDocumentDTO2);
        tenantDocumentDTO2.setId(tenantDocumentDTO1.getId());
        assertThat(tenantDocumentDTO1).isEqualTo(tenantDocumentDTO2);
        tenantDocumentDTO2.setId(2L);
        assertThat(tenantDocumentDTO1).isNotEqualTo(tenantDocumentDTO2);
        tenantDocumentDTO1.setId(null);
        assertThat(tenantDocumentDTO1).isNotEqualTo(tenantDocumentDTO2);
    }
}
