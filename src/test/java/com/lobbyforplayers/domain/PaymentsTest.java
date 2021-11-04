package com.lobbyforplayers.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.lobbyforplayers.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Payments.class);
        Payments payments1 = new Payments();
        payments1.setId("id1");
        Payments payments2 = new Payments();
        payments2.setId(payments1.getId());
        assertThat(payments1).isEqualTo(payments2);
        payments2.setId("id2");
        assertThat(payments1).isNotEqualTo(payments2);
        payments1.setId(null);
        assertThat(payments1).isNotEqualTo(payments2);
    }
}
