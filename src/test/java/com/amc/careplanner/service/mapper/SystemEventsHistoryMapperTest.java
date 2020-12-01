package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SystemEventsHistoryMapperTest {

    private SystemEventsHistoryMapper systemEventsHistoryMapper;

    @BeforeEach
    public void setUp() {
        systemEventsHistoryMapper = new SystemEventsHistoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(systemEventsHistoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(systemEventsHistoryMapper.fromId(null)).isNull();
    }
}
