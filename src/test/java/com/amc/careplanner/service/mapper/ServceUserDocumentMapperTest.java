package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ServceUserDocumentMapperTest {

    private ServceUserDocumentMapper servceUserDocumentMapper;

    @BeforeEach
    public void setUp() {
        servceUserDocumentMapper = new ServceUserDocumentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(servceUserDocumentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(servceUserDocumentMapper.fromId(null)).isNull();
    }
}
