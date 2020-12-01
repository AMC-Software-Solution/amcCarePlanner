package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PayrollMapperTest {

    private PayrollMapper payrollMapper;

    @BeforeEach
    public void setUp() {
        payrollMapper = new PayrollMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(payrollMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(payrollMapper.fromId(null)).isNull();
    }
}
