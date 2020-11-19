package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class ServiceUserLocationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceUserLocationDTO.class);
        ServiceUserLocationDTO serviceUserLocationDTO1 = new ServiceUserLocationDTO();
        serviceUserLocationDTO1.setId(1L);
        ServiceUserLocationDTO serviceUserLocationDTO2 = new ServiceUserLocationDTO();
        assertThat(serviceUserLocationDTO1).isNotEqualTo(serviceUserLocationDTO2);
        serviceUserLocationDTO2.setId(serviceUserLocationDTO1.getId());
        assertThat(serviceUserLocationDTO1).isEqualTo(serviceUserLocationDTO2);
        serviceUserLocationDTO2.setId(2L);
        assertThat(serviceUserLocationDTO1).isNotEqualTo(serviceUserLocationDTO2);
        serviceUserLocationDTO1.setId(null);
        assertThat(serviceUserLocationDTO1).isNotEqualTo(serviceUserLocationDTO2);
    }
}
