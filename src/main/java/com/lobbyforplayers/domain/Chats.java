package com.lobbyforplayers.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Chats.
 */
@Document(collection = "chats")
public class Chats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("from_user_id")
    private String fromUserId;

    @NotNull
    @Field("to_user_id")
    private String toUserId;

    @NotNull
    @Field("time_stamp")
    private Instant timeStamp;

    @NotNull
    @Size(min = 1, max = 256)
    @Field("message")
    private String message;

    @NotNull
    @Size(min = 2, max = 10)
    @Field("language")
    private String language;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Chats id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromUserId() {
        return this.fromUserId;
    }

    public Chats fromUserId(String fromUserId) {
        this.setFromUserId(fromUserId);
        return this;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return this.toUserId;
    }

    public Chats toUserId(String toUserId) {
        this.setToUserId(toUserId);
        return this;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public Instant getTimeStamp() {
        return this.timeStamp;
    }

    public Chats timeStamp(Instant timeStamp) {
        this.setTimeStamp(timeStamp);
        return this;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return this.message;
    }

    public Chats message(String message) {
        this.setMessage(message);
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLanguage() {
        return this.language;
    }

    public Chats language(String language) {
        this.setLanguage(language);
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Chats)) {
            return false;
        }
        return id != null && id.equals(((Chats) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Chats{" +
            "id=" + getId() +
            ", fromUserId='" + getFromUserId() + "'" +
            ", toUserId='" + getToUserId() + "'" +
            ", timeStamp='" + getTimeStamp() + "'" +
            ", message='" + getMessage() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
