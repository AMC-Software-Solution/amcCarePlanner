package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DisabilityTypeMapperTest {

    private DisabilityTypeMapper disabilityTypeMapper;

    @BeforeEach
    public void setUp() {
        disabilityTypeMapper = new DisabilityTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(disabilityTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(disabilityTypeMapper.fromId(null)).isNull();
    }
}
