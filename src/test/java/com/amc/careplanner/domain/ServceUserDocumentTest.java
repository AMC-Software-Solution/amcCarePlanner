package com.amc.careplanner.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class ServceUserDocumentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServceUserDocument.class);
        ServceUserDocument servceUserDocument1 = new ServceUserDocument();
        servceUserDocument1.setId(1L);
        ServceUserDocument servceUserDocument2 = new ServceUserDocument();
        servceUserDocument2.setId(servceUserDocument1.getId());
        assertThat(servceUserDocument1).isEqualTo(servceUserDocument2);
        servceUserDocument2.setId(2L);
        assertThat(servceUserDocument1).isNotEqualTo(servceUserDocument2);
        servceUserDocument1.setId(null);
        assertThat(servceUserDocument1).isNotEqualTo(servceUserDocument2);
    }
}
