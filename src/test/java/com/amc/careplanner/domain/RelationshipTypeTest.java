package com.amc.careplanner.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class RelationshipTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RelationshipType.class);
        RelationshipType relationshipType1 = new RelationshipType();
        relationshipType1.setId(1L);
        RelationshipType relationshipType2 = new RelationshipType();
        relationshipType2.setId(relationshipType1.getId());
        assertThat(relationshipType1).isEqualTo(relationshipType2);
        relationshipType2.setId(2L);
        assertThat(relationshipType1).isNotEqualTo(relationshipType2);
        relationshipType1.setId(null);
        assertThat(relationshipType1).isNotEqualTo(relationshipType2);
    }
}
