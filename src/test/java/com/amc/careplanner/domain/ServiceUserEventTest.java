package com.amc.careplanner.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class ServiceUserEventTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceUserEvent.class);
        ServiceUserEvent serviceUserEvent1 = new ServiceUserEvent();
        serviceUserEvent1.setId(1L);
        ServiceUserEvent serviceUserEvent2 = new ServiceUserEvent();
        serviceUserEvent2.setId(serviceUserEvent1.getId());
        assertThat(serviceUserEvent1).isEqualTo(serviceUserEvent2);
        serviceUserEvent2.setId(2L);
        assertThat(serviceUserEvent1).isNotEqualTo(serviceUserEvent2);
        serviceUserEvent1.setId(null);
        assertThat(serviceUserEvent1).isNotEqualTo(serviceUserEvent2);
    }
}
