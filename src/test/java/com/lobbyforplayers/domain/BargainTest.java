package com.lobbyforplayers.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.lobbyforplayers.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BargainTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bargain.class);
        Bargain bargain1 = new Bargain();
        bargain1.setId(1L);
        Bargain bargain2 = new Bargain();
        bargain2.setId(bargain1.getId());
        assertThat(bargain1).isEqualTo(bargain2);
        bargain2.setId(2L);
        assertThat(bargain1).isNotEqualTo(bargain2);
        bargain1.setId(null);
        assertThat(bargain1).isNotEqualTo(bargain2);
    }
}
