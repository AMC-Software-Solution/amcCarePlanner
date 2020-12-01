package com.amc.careplanner.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class SystemEventsHistoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemEventsHistory.class);
        SystemEventsHistory systemEventsHistory1 = new SystemEventsHistory();
        systemEventsHistory1.setId(1L);
        SystemEventsHistory systemEventsHistory2 = new SystemEventsHistory();
        systemEventsHistory2.setId(systemEventsHistory1.getId());
        assertThat(systemEventsHistory1).isEqualTo(systemEventsHistory2);
        systemEventsHistory2.setId(2L);
        assertThat(systemEventsHistory1).isNotEqualTo(systemEventsHistory2);
        systemEventsHistory1.setId(null);
        assertThat(systemEventsHistory1).isNotEqualTo(systemEventsHistory2);
    }
}
