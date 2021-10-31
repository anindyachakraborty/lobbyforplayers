package com.lobbyforplayers.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.lobbyforplayers.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Details.class);
        Details details1 = new Details();
        details1.setId("ABC");
        Details details2 = new Details();
        details2.setId(details1.getId());
        assertThat(details1).isEqualTo(details2);
        details2.setId("XYZ");
        assertThat(details1).isNotEqualTo(details2);
        details1.setId(null);
        assertThat(details1).isNotEqualTo(details2);
    }
}
