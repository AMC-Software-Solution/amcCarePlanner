package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class ServiceUserContactDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceUserContactDTO.class);
        ServiceUserContactDTO serviceUserContactDTO1 = new ServiceUserContactDTO();
        serviceUserContactDTO1.setId(1L);
        ServiceUserContactDTO serviceUserContactDTO2 = new ServiceUserContactDTO();
        assertThat(serviceUserContactDTO1).isNotEqualTo(serviceUserContactDTO2);
        serviceUserContactDTO2.setId(serviceUserContactDTO1.getId());
        assertThat(serviceUserContactDTO1).isEqualTo(serviceUserContactDTO2);
        serviceUserContactDTO2.setId(2L);
        assertThat(serviceUserContactDTO1).isNotEqualTo(serviceUserContactDTO2);
        serviceUserContactDTO1.setId(null);
        assertThat(serviceUserContactDTO1).isNotEqualTo(serviceUserContactDTO2);
    }
}
