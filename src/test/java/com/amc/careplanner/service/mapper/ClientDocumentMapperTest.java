package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ClientDocumentMapperTest {

    private ClientDocumentMapper clientDocumentMapper;

    @BeforeEach
    public void setUp() {
        clientDocumentMapper = new ClientDocumentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(clientDocumentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(clientDocumentMapper.fromId(null)).isNull();
    }
}
