package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TravelMapperTest {

    private TravelMapper travelMapper;

    @BeforeEach
    public void setUp() {
        travelMapper = new TravelMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(travelMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(travelMapper.fromId(null)).isNull();
    }
}
