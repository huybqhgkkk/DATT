package com.aladin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.aladin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RecruitmenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recruitmen.class);
        Recruitmen recruitmen1 = new Recruitmen();
        recruitmen1.setId(1L);
        Recruitmen recruitmen2 = new Recruitmen();
        recruitmen2.setId(recruitmen1.getId());
        assertThat(recruitmen1).isEqualTo(recruitmen2);
        recruitmen2.setId(2L);
        assertThat(recruitmen1).isNotEqualTo(recruitmen2);
        recruitmen1.setId(null);
        assertThat(recruitmen1).isNotEqualTo(recruitmen2);
    }
}
