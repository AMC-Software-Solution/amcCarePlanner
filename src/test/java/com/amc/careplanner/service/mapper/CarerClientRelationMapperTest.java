package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CarerClientRelationMapperTest {

    private CarerClientRelationMapper carerClientRelationMapper;

    @BeforeEach
    public void setUp() {
        carerClientRelationMapper = new CarerClientRelationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(carerClientRelationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(carerClientRelationMapper.fromId(null)).isNull();
    }
}
