package com.amc.careplanner.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class TimesheetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Timesheet.class);
        Timesheet timesheet1 = new Timesheet();
        timesheet1.setId(1L);
        Timesheet timesheet2 = new Timesheet();
        timesheet2.setId(timesheet1.getId());
        assertThat(timesheet1).isEqualTo(timesheet2);
        timesheet2.setId(2L);
        assertThat(timesheet1).isNotEqualTo(timesheet2);
        timesheet1.setId(null);
        assertThat(timesheet1).isNotEqualTo(timesheet2);
    }
}
