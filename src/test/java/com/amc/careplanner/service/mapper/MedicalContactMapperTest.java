package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MedicalContactMapperTest {

    private MedicalContactMapper medicalContactMapper;

    @BeforeEach
    public void setUp() {
        medicalContactMapper = new MedicalContactMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(medicalContactMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(medicalContactMapper.fromId(null)).isNull();
    }
}
