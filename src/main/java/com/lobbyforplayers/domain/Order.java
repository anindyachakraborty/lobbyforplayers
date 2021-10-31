package com.lobbyforplayers.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "seller_name", nullable = false)
    private String sellerName;

    @NotNull
    @Column(name = "buyer_name", nullable = false)
    private String buyerName;

    @NotNull
    @Column(name = "price_settled", nullable = false)
    private Double priceSettled;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "completed", nullable = false)
    private Boolean completed;

    @JsonIgnoreProperties(value = { "order", "bargains", "tags" }, allowSetters = true)
    @OneToOne(mappedBy = "order")
    private Item item;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Order id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
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
