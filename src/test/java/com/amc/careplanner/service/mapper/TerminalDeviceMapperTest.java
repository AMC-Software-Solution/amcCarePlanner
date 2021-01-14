package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TerminalDeviceMapperTest {

    private TerminalDeviceMapper terminalDeviceMapper;

    @BeforeEach
    public void setUp() {
        terminalDeviceMapper = new TerminalDeviceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(terminalDeviceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(terminalDeviceMapper.fromId(null)).isNull();
    }
}
