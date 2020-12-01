package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class TerminalDeviceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TerminalDeviceDTO.class);
        TerminalDeviceDTO terminalDeviceDTO1 = new TerminalDeviceDTO();
        terminalDeviceDTO1.setId(1L);
        TerminalDeviceDTO terminalDeviceDTO2 = new TerminalDeviceDTO();
        assertThat(terminalDeviceDTO1).isNotEqualTo(terminalDeviceDTO2);
        terminalDeviceDTO2.setId(terminalDeviceDTO1.getId());
        assertThat(terminalDeviceDTO1).isEqualTo(terminalDeviceDTO2);
        terminalDeviceDTO2.setId(2L);
        assertThat(terminalDeviceDTO1).isNotEqualTo(terminalDeviceDTO2);
        terminalDeviceDTO1.setId(null);
        assertThat(terminalDeviceDTO1).isNotEqualTo(terminalDeviceDTO2);
    }
}
