package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class SystemEventsHistoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemEventsHistoryDTO.class);
        SystemEventsHistoryDTO systemEventsHistoryDTO1 = new SystemEventsHistoryDTO();
        systemEventsHistoryDTO1.setId(1L);
        SystemEventsHistoryDTO systemEventsHistoryDTO2 = new SystemEventsHistoryDTO();
        assertThat(systemEventsHistoryDTO1).isNotEqualTo(systemEventsHistoryDTO2);
        systemEventsHistoryDTO2.setId(systemEventsHistoryDTO1.getId());
        assertThat(systemEventsHistoryDTO1).isEqualTo(systemEventsHistoryDTO2);
        systemEventsHistoryDTO2.setId(2L);
        assertThat(systemEventsHistoryDTO1).isNotEqualTo(systemEventsHistoryDTO2);
        systemEventsHistoryDTO1.setId(null);
        assertThat(systemEventsHistoryDTO1).isNotEqualTo(systemEventsHistoryDTO2);
    }
}
