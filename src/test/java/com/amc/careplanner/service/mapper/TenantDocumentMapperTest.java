package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TenantDocumentMapperTest {

    private TenantDocumentMapper tenantDocumentMapper;

    @BeforeEach
    public void setUp() {
        tenantDocumentMapper = new TenantDocumentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tenantDocumentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tenantDocumentMapper.fromId(null)).isNull();
    }
}
