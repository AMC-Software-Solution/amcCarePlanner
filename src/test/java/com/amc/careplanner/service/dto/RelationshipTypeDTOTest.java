package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class RelationshipTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RelationshipTypeDTO.class);
        RelationshipTypeDTO relationshipTypeDTO1 = new RelationshipTypeDTO();
        relationshipTypeDTO1.setId(1L);
        RelationshipTypeDTO relationshipTypeDTO2 = new RelationshipTypeDTO();
        assertThat(relationshipTypeDTO1).isNotEqualTo(relationshipTypeDTO2);
        relationshipTypeDTO2.setId(relationshipTypeDTO1.getId());
        assertThat(relationshipTypeDTO1).isEqualTo(relationshipTypeDTO2);
        relationshipTypeDTO2.setId(2L);
        assertThat(relationshipTypeDTO1).isNotEqualTo(relationshipTypeDTO2);
        relationshipTypeDTO1.setId(null);
        assertThat(relationshipTypeDTO1).isNotEqualTo(relationshipTypeDTO2);
    }
}
