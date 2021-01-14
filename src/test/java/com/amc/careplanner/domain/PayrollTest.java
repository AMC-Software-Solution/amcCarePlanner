package com.amc.careplanner.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.amc.careplanner.web.rest.TestUtil;

public class PayrollTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Payroll.class);
        Payroll payroll1 = new Payroll();
        payroll1.setId(1L);
        Payroll payroll2 = new Payroll();
        payroll2.setId(payroll1.getId());
        assertThat(payroll1).isEqualTo(payroll2);
        payroll2.setId(2L);
        assertThat(payroll1).isNotEqualTo(payroll2);
        payroll1.setId(null);
        assertThat(payroll1).isNotEqualTo(payroll2);
    }
}
