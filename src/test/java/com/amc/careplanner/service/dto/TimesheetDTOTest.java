package com.amc.careplanner.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class TimesheetDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimesheetDTO.class);
        TimesheetDTO timesheetDTO1 = new TimesheetDTO();
        timesheetDTO1.setId(1L);
        TimesheetDTO timesheetDTO2 = new TimesheetDTO();
        assertThat(timesheetDTO1).isNotEqualTo(timesheetDTO2);
        timesheetDTO2.setId(timesheetDTO1.getId());
        assertThat(timesheetDTO1).isEqualTo(timesheetDTO2);
        timesheetDTO2.setId(2L);
        assertThat(timesheetDTO1).isNotEqualTo(timesheetDTO2);
        timesheetDTO1.setId(null);
        assertThat(timesheetDTO1).isNotEqualTo(timesheetDTO2);
    }
}
