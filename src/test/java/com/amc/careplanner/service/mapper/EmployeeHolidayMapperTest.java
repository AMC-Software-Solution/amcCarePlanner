package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeHolidayMapperTest {

    private EmployeeHolidayMapper employeeHolidayMapper;

    @BeforeEach
    public void setUp() {
        employeeHolidayMapper = new EmployeeHolidayMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(employeeHolidayMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(employeeHolidayMapper.fromId(null)).isNull();
    }
}
