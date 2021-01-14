package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ServiceUserContactMapperTest {

    private ServiceUserContactMapper serviceUserContactMapper;

    @BeforeEach
    public void setUp() {
        serviceUserContactMapper = new ServiceUserContactMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(serviceUserContactMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(serviceUserContactMapper.fromId(null)).isNull();
    }
}
