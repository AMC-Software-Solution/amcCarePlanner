package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EmergencyContactMapperTest {

    private EmergencyContactMapper emergencyContactMapper;

    @BeforeEach
    public void setUp() {
        emergencyContactMapper = new EmergencyContactMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(emergencyContactMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(emergencyContactMapper.fromId(null)).isNull();
    }
}
