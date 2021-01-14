package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ExtraDataMapperTest {

    private ExtraDataMapper extraDataMapper;

    @BeforeEach
    public void setUp() {
        extraDataMapper = new ExtraDataMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(extraDataMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(extraDataMapper.fromId(null)).isNull();
    }
}
