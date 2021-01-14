package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DisabilityMapperTest {

    private DisabilityMapper disabilityMapper;

    @BeforeEach
    public void setUp() {
        disabilityMapper = new DisabilityMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(disabilityMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(disabilityMapper.fromId(null)).isNull();
    }
}
