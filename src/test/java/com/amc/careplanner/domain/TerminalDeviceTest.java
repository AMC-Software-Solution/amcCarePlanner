package com.amc.careplanner.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class TerminalDeviceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TerminalDevice.class);
        TerminalDevice terminalDevice1 = new TerminalDevice();
        terminalDevice1.setId(1L);
        TerminalDevice terminalDevice2 = new TerminalDevice();
        terminalDevice2.setId(terminalDevice1.getId());
        assertThat(terminalDevice1).isEqualTo(terminalDevice2);
        terminalDevice2.setId(2L);
        assertThat(terminalDevice1).isNotEqualTo(terminalDevice2);
        terminalDevice1.setId(null);
        assertThat(terminalDevice1).isNotEqualTo(terminalDevice2);
    }
}
