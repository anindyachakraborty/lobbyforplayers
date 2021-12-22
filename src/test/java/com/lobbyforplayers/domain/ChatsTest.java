package com.lobbyforplayers.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.lobbyforplayers.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChatsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chats.class);
        Chats chats1 = new Chats();
        chats1.setId("id1");
        Chats chats2 = new Chats();
        chats2.setId(chats1.getId());
        assertThat(chats1).isEqualTo(chats2);
        chats2.setId("id2");
        assertThat(chats1).isNotEqualTo(chats2);
        chats1.setId(null);
        assertThat(chats1).isNotEqualTo(chats2);
    }
}
