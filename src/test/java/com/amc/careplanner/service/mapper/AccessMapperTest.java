package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AccessMapperTest {

    private AccessMapper accessMapper;

    @BeforeEach
    public void setUp() {
        accessMapper = new AccessMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(accessMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(accessMapper.fromId(null)).isNull();
    }
}
