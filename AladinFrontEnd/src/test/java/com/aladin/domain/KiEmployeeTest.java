package com.aladin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.aladin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KiEmployeeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KiEmployee.class);
        KiEmployee kiEmployee1 = new KiEmployee();
        kiEmployee1.setId(1L);
        KiEmployee kiEmployee2 = new KiEmployee();
        kiEmployee2.setId(kiEmployee1.getId());
        assertThat(kiEmployee1).isEqualTo(kiEmployee2);
        kiEmployee2.setId(2L);
        assertThat(kiEmployee1).isNotEqualTo(kiEmployee2);
        kiEmployee1.setId(null);
        assertThat(kiEmployee1).isNotEqualTo(kiEmployee2);
    }
}
