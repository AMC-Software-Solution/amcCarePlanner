package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeDocumentMapperTest {

    private EmployeeDocumentMapper employeeDocumentMapper;

    @BeforeEach
    public void setUp() {
        employeeDocumentMapper = new EmployeeDocumentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(employeeDocumentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(employeeDocumentMapper.fromId(null)).isNull();
    }
}
