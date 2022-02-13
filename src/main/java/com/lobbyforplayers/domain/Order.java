package com.lobbyforplayers.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Order.
 */
@Document(collection = "order")
public class Order extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("seller_name")
    private String sellerName;

    @NotNull
    @Field("buyer_name")
    private String buyerName;

    @NotNull
    @Field("price_settled")
    private Double priceSettled;

    @NotNull
    @Field("status")
    private String status;

    @NotNull
    @Field("completed")
    private Boolean completed;

    @DBRef
    @JsonIgnoreProperties(value = { "order" }, allowSetters = true)
    private Item item;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Order id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSellerName() {
        return this.sellerName;
    }

    public Order sellerName(String sellerName) {
        this.setSellerName(sellerName);
        return this;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getBuyerName() {
        return this.buyerName;
    }

    public Order buyerName(String buyerName) {
        this.setBuyerName(buyerName);
        return this;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public Double getPriceSettled() {
        return this.priceSettled;
    }

    public Order priceSettled(Double priceSettled) {
        this.setPriceSettled(priceSettled);
        return this;
    }

    public void setPriceSettled(Double priceSettled) {
        this.priceSettled = priceSettled;
    }

    public String getStatus() {
        return this.status;
    }

    public Order status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getCompleted() {
        return this.completed;
    }

    public Order completed(Boolean completed) {
        this.setCompleted(completed);
        return this;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        if (this.item != null) {
            this.item.setOrder(null);
        }
        if (item != null) {
            item.setOrder(this);
        }
        this.item = item;
    }

    public Order item(Item item) {
        this.setItem(item);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", sellerName='" + getSellerName() + "'" +
            ", buyerName='" + getBuyerName() + "'" +
            ", priceSettled=" + getPriceSettled() +
            ", status='" + getStatus() + "'" +
            ", completed='" + getCompleted() + "'" +
            "}";
    }
}
