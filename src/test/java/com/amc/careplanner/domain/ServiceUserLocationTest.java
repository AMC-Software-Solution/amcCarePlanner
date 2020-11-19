package com.amc.careplanner.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class ServiceUserLocationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceUserLocation.class);
        ServiceUserLocation serviceUserLocation1 = new ServiceUserLocation();
        serviceUserLocation1.setId(1L);
        ServiceUserLocation serviceUserLocation2 = new ServiceUserLocation();
        serviceUserLocation2.setId(serviceUserLocation1.getId());
        assertThat(serviceUserLocation1).isEqualTo(serviceUserLocation2);
        serviceUserLocation2.setId(2L);
        assertThat(serviceUserLocation1).isNotEqualTo(serviceUserLocation2);
        serviceUserLocation1.setId(null);
        assertThat(serviceUserLocation1).isNotEqualTo(serviceUserLocation2);
    }
}
