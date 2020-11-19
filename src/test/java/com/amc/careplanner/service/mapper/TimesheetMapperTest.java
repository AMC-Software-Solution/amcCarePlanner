package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TimesheetMapperTest {

    private TimesheetMapper timesheetMapper;

    @BeforeEach
    public void setUp() {
        timesheetMapper = new TimesheetMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(timesheetMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(timesheetMapper.fromId(null)).isNull();
    }
}
