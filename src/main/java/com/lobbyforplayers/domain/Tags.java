package com.lobbyforplayers.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Tags.
 */
@Document(collection = "tags")
public class Tags implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 3)
    @Field("tag")
    private String tag;

    @NotNull
    @Size(min = 2, max = 10)
    @Field("language")
    private String language;

    @DBRef
    @Field("sentries")
    @JsonIgnoreProperties(value = { "order", "bargains", "tags" }, allowSetters = true)
    private Set<Item> sentries = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Tags id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTag() {
        return this.tag;
    }

    public Tags tag(String tag) {
        this.setTag(tag);
        return this;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getLanguage() {
        return this.language;
    }

    public Tags language(String language) {
        this.setLanguage(language);
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Set<Item> getSentries() {
        return this.sentries;
    }

    public void setSentries(Set<Item> items) {
        if (this.sentries != null) {
            this.sentries.forEach(i -> i.removeTags(this));
        }
        if (items != null) {
            items.forEach(i -> i.addTags(this));
        }
        this.sentries = items;
    }

    public Tags sentries(Set<Item> items) {
        this.setSentries(items);
        return this;
    }

    public Tags addSentry(Item item) {
        this.sentries.add(item);
        item.getTags().add(this);
        return this;
    }

    public Tags removeSentry(Item item) {
        this.sentries.remove(item);
        item.getTags().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tags)) {
            return false;
        }
        return id != null && id.equals(((Tags) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tags{" +
            "id=" + getId() +
            ", tag='" + getTag() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
