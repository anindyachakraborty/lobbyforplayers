package com.lobbyforplayers.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Bargain.
 */
@Document(collection = "bargain")
public class Bargain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("bargain_price")
    private Double bargainPrice;

    @NotNull
    @Field("item_id")
    private String itemId;

    @NotNull
    @Field("seller_approved")
    private Boolean sellerApproved;

    @NotNull
    @Field("buyer_approved")
    private Boolean buyerApproved;

    @NotNull
    @Field("seller_id")
    private String sellerId;

    @NotNull
    @Field("buyer_id")
    private String buyerId;

    @DBRef
    @Field("item")
    @JsonIgnoreProperties(value = { "order", "bargains", "tags" }, allowSetters = true)
    private Item item;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Bargain id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getBargainPrice() {
        return this.bargainPrice;
    }

    public Bargain bargainPrice(Double bargainPrice) {
        this.setBargainPrice(bargainPrice);
        return this;
    }

    public void setBargainPrice(Double bargainPrice) {
        this.bargainPrice = bargainPrice;
    }

    public String getItemId() {
        return this.itemId;
    }

    public Bargain itemId(String itemId) {
        this.setItemId(itemId);
        return this;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Boolean getSellerApproved() {
        return this.sellerApproved;
    }

    public Bargain sellerApproved(Boolean sellerApproved) {
        this.setSellerApproved(sellerApproved);
        return this;
    }

    public void setSellerApproved(Boolean sellerApproved) {
        this.sellerApproved = sellerApproved;
    }

    public Boolean getBuyerApproved() {
        return this.buyerApproved;
    }

    public Bargain buyerApproved(Boolean buyerApproved) {
        this.setBuyerApproved(buyerApproved);
        return this;
    }

    public void setBuyerApproved(Boolean buyerApproved) {
        this.buyerApproved = buyerApproved;
    }

    public String getSellerId() {
        return this.sellerId;
    }

    public Bargain sellerId(String sellerId) {
        this.setSellerId(sellerId);
        return this;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getBuyerId() {
        return this.buyerId;
    }

    public Bargain buyerId(String buyerId) {
        this.setBuyerId(buyerId);
        return this;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Bargain item(Item item) {
        this.setItem(item);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bargain)) {
            return false;
        }
        return id != null && id.equals(((Bargain) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bargain{" +
            "id=" + getId() +
            ", bargainPrice=" + getBargainPrice() +
            ", itemId='" + getItemId() + "'" +
            ", sellerApproved='" + getSellerApproved() + "'" +
            ", buyerApproved='" + getBuyerApproved() + "'" +
            ", sellerId='" + getSellerId() + "'" +
            ", buyerId='" + getBuyerId() + "'" +
            "}";
    }
}
