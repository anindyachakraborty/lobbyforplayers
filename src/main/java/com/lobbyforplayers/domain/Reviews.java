package com.lobbyforplayers.domain;

import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Reviews.
 */
@Document(collection = "reviews")
public class Reviews implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("question")
    private String question;

    @NotNull
    @Field("rating")
    private Long rating;

    @NotNull
    @Field("username")
    private String username;

    @Field("order_id")
    private String orderId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Reviews id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return this.question;
    }

    public Reviews question(String question) {
        this.setQuestion(question);
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Long getRating() {
        return this.rating;
    }

    public Reviews rating(Long rating) {
        this.setRating(rating);
        return this;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public String getUsername() {
        return this.username;
    }

    public Reviews username(String username) {
        this.setUsername(username);
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public Reviews orderId(String orderId) {
        this.setOrderId(orderId);
        return this;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reviews)) {
            return false;
        }
        return id != null && id.equals(((Reviews) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reviews{" +
            "id=" + getId() +
            ", question='" + getQuestion() + "'" +
            ", rating=" + getRating() +
            ", username='" + getUsername() + "'" +
            ", orderId='" + getOrderId() + "'" +
            "}";
    }
}
