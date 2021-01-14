package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class ServiceUserEventDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceUserEventDTO.class);
        ServiceUserEventDTO serviceUserEventDTO1 = new ServiceUserEventDTO();
        serviceUserEventDTO1.setId(1L);
        ServiceUserEventDTO serviceUserEventDTO2 = new ServiceUserEventDTO();
        assertThat(serviceUserEventDTO1).isNotEqualTo(serviceUserEventDTO2);
        serviceUserEventDTO2.setId(serviceUserEventDTO1.getId());
        assertThat(serviceUserEventDTO1).isEqualTo(serviceUserEventDTO2);
        serviceUserEventDTO2.setId(2L);
        assertThat(serviceUserEventDTO1).isNotEqualTo(serviceUserEventDTO2);
        serviceUserEventDTO1.setId(null);
        assertThat(serviceUserEventDTO1).isNotEqualTo(serviceUserEventDTO2);
    }
}
