package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ServiceUserLocationMapperTest {

    private ServiceUserLocationMapper serviceUserLocationMapper;

    @BeforeEach
    public void setUp() {
        serviceUserLocationMapper = new ServiceUserLocationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(serviceUserLocationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(serviceUserLocationMapper.fromId(null)).isNull();
    }
}
