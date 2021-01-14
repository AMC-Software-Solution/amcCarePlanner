package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ServiceUserEventMapperTest {

    private ServiceUserEventMapper serviceUserEventMapper;

    @BeforeEach
    public void setUp() {
        serviceUserEventMapper = new ServiceUserEventMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(serviceUserEventMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(serviceUserEventMapper.fromId(null)).isNull();
    }
}
