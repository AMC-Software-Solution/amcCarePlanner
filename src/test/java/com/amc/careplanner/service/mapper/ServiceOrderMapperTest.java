package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ServiceOrderMapperTest {

    private ServiceOrderMapper serviceOrderMapper;

    @BeforeEach
    public void setUp() {
        serviceOrderMapper = new ServiceOrderMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(serviceOrderMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(serviceOrderMapper.fromId(null)).isNull();
    }
}
