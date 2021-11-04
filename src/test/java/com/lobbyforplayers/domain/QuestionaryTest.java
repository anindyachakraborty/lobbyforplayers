package com.lobbyforplayers.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.lobbyforplayers.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QuestionaryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Questionary.class);
        Questionary questionary1 = new Questionary();
        questionary1.setId("id1");
        Questionary questionary2 = new Questionary();
        questionary2.setId(questionary1.getId());
        assertThat(questionary1).isEqualTo(questionary2);
        questionary2.setId("id2");
        assertThat(questionary1).isNotEqualTo(questionary2);
        questionary1.setId(null);
        assertThat(questionary1).isNotEqualTo(questionary2);
    }
}
