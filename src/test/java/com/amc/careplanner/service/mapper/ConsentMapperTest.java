package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ConsentMapperTest {

    private ConsentMapper consentMapper;

    @BeforeEach
    public void setUp() {
        consentMapper = new ConsentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(consentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(consentMapper.fromId(null)).isNull();
    }
}
