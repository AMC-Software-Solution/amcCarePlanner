package com.amc.careplanner.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class TenantDocumentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TenantDocument.class);
        TenantDocument tenantDocument1 = new TenantDocument();
        tenantDocument1.setId(1L);
        TenantDocument tenantDocument2 = new TenantDocument();
        tenantDocument2.setId(tenantDocument1.getId());
        assertThat(tenantDocument1).isEqualTo(tenantDocument2);
        tenantDocument2.setId(2L);
        assertThat(tenantDocument1).isNotEqualTo(tenantDocument2);
        tenantDocument1.setId(null);
        assertThat(tenantDocument1).isNotEqualTo(tenantDocument2);
    }
}
