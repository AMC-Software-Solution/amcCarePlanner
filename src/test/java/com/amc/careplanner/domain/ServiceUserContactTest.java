package com.amc.careplanner.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class ServiceUserContactTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceUserContact.class);
        ServiceUserContact serviceUserContact1 = new ServiceUserContact();
        serviceUserContact1.setId(1L);
        ServiceUserContact serviceUserContact2 = new ServiceUserContact();
        serviceUserContact2.setId(serviceUserContact1.getId());
        assertThat(serviceUserContact1).isEqualTo(serviceUserContact2);
        serviceUserContact2.setId(2L);
        assertThat(serviceUserContact1).isNotEqualTo(serviceUserContact2);
        serviceUserContact1.setId(null);
        assertThat(serviceUserContact1).isNotEqualTo(serviceUserContact2);
    }
}
