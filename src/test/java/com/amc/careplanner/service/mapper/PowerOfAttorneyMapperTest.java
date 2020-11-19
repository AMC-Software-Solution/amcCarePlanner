package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PowerOfAttorneyMapperTest {

    private PowerOfAttorneyMapper powerOfAttorneyMapper;

    @BeforeEach
    public void setUp() {
        powerOfAttorneyMapper = new PowerOfAttorneyMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(powerOfAttorneyMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(powerOfAttorneyMapper.fromId(null)).isNull();
    }
}
