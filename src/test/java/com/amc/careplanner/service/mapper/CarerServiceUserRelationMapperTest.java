package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CarerServiceUserRelationMapperTest {

    private CarerServiceUserRelationMapper carerServiceUserRelationMapper;

    @BeforeEach
    public void setUp() {
        carerServiceUserRelationMapper = new CarerServiceUserRelationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(carerServiceUserRelationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(carerServiceUserRelationMapper.fromId(null)).isNull();
    }
}
