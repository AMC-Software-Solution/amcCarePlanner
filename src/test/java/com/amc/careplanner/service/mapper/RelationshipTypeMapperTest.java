package com.amc.careplanner.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RelationshipTypeMapperTest {

    private RelationshipTypeMapper relationshipTypeMapper;

    @BeforeEach
    public void setUp() {
        relationshipTypeMapper = new RelationshipTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(relationshipTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(relationshipTypeMapper.fromId(null)).isNull();
    }
}
