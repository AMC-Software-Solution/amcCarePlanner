package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeLocationMapperTest {

    private EmployeeLocationMapper employeeLocationMapper;

    @BeforeEach
    public void setUp() {
        employeeLocationMapper = new EmployeeLocationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(employeeLocationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(employeeLocationMapper.fromId(null)).isNull();
    }
}
