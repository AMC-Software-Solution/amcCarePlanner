package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeDesignationMapperTest {

    private EmployeeDesignationMapper employeeDesignationMapper;

    @BeforeEach
    public void setUp() {
        employeeDesignationMapper = new EmployeeDesignationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(employeeDesignationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(employeeDesignationMapper.fromId(null)).isNull();
    }
}
