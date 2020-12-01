package com.amc.careplanner.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class ServiceOrderTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOrder.class);
        ServiceOrder serviceOrder1 = new ServiceOrder();
        serviceOrder1.setId(1L);
        ServiceOrder serviceOrder2 = new ServiceOrder();
        serviceOrder2.setId(serviceOrder1.getId());
        assertThat(serviceOrder1).isEqualTo(serviceOrder2);
        serviceOrder2.setId(2L);
        assertThat(serviceOrder1).isNotEqualTo(serviceOrder2);
        serviceOrder1.setId(null);
        assertThat(serviceOrder1).isNotEqualTo(serviceOrder2);
    }
}
