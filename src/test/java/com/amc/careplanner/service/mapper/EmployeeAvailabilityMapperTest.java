package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeAvailabilityMapperTest {

    private EmployeeAvailabilityMapper employeeAvailabilityMapper;

    @BeforeEach
    public void setUp() {
        employeeAvailabilityMapper = new EmployeeAvailabilityMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(employeeAvailabilityMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(employeeAvailabilityMapper.fromId(null)).isNull();
    }
}
