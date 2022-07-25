package com.aladin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.aladin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmailtemplateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Emailtemplate.class);
        Emailtemplate emailtemplate1 = new Emailtemplate();
        emailtemplate1.setId(1L);
        Emailtemplate emailtemplate2 = new Emailtemplate();
        emailtemplate2.setId(emailtemplate1.getId());
        assertThat(emailtemplate1).isEqualTo(emailtemplate2);
        emailtemplate2.setId(2L);
        assertThat(emailtemplate1).isNotEqualTo(emailtemplate2);
        emailtemplate1.setId(null);
        assertThat(emailtemplate1).isNotEqualTo(emailtemplate2);
    }
}
