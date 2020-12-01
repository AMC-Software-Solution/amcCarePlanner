package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EligibilityTypeMapperTest {

    private EligibilityTypeMapper eligibilityTypeMapper;

    @BeforeEach
    public void setUp() {
        eligibilityTypeMapper = new EligibilityTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(eligibilityTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(eligibilityTypeMapper.fromId(null)).isNull();
    }
}
