package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommunicationMapperTest {

    private CommunicationMapper communicationMapper;

    @BeforeEach
    public void setUp() {
        communicationMapper = new CommunicationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(communicationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(communicationMapper.fromId(null)).isNull();
    }
}
