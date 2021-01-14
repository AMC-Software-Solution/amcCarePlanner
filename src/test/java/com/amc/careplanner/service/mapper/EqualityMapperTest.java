package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EqualityMapperTest {

    private EqualityMapper equalityMapper;

    @BeforeEach
    public void setUp() {
        equalityMapper = new EqualityMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(equalityMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(equalityMapper.fromId(null)).isNull();
    }
}
