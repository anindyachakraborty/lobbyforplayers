package com.lobbyforplayers.domain;

import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Payments.
 */
@Document(collection = "payments")
public class Payments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("buyer_user_id")
    private String buyerUserId;

    @NotNull
    @Field("seller_user_id")
    private String sellerUserId;

    @NotNull
    @Field("seller_recieved")
    private Boolean sellerRecieved;

    @Field("buyer_transaction_id")
    private String buyerTransactionId;

    @Field("seller_transaction_id")
    private String sellerTransactionId;

    @NotNull
    @Field("order_id")
    private String orderId;

    @NotNull
    @Field("package_id")
    private String packageId;

    @NotNull
    @Field("amount")
    private Double amount;

    @Field("game")
    private String game;

    @NotNull
    @Field("status")
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Payments id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuyerUserId() {
        return this.buyerUserId;
    }

    public Payments buyerUserId(String buyerUserId) {
        this.setBuyerUserId(buyerUserId);
        return this;
    }

    public void setBuyerUserId(String buyerUserId) {
        this.buyerUserId = buyerUserId;
    }

    public String getSellerUserId() {
        return this.sellerUserId;
    }

    public Payments sellerUserId(String sellerUserId) {
        this.setSellerUserId(sellerUserId);
        return this;
    }

    public void setSellerUserId(String sellerUserId) {
        this.sellerUserId = sellerUserId;
    }

    public Boolean getSellerRecieved() {
        return this.sellerRecieved;
    }

    public Payments sellerRecieved(Boolean sellerRecieved) {
        this.setSellerRecieved(sellerRecieved);
        return this;
    }

    public void setSellerRecieved(Boolean sellerRecieved) {
        this.sellerRecieved = sellerRecieved;
    }

    public String getBuyerTransactionId() {
        return this.buyerTransactionId;
    }

    public Payments buyerTransactionId(String buyerTransactionId) {
        this.setBuyerTransactionId(buyerTransactionId);
        return this;
    }

    public void setBuyerTransactionId(String buyerTransactionId) {
        this.buyerTransactionId = buyerTransactionId;
    }

    public String getSellerTransactionId() {
        return this.sellerTransactionId;
    }

    public Payments sellerTransactionId(String sellerTransactionId) {
        this.setSellerTransactionId(sellerTransactionId);
        return this;
    }

    public void setSellerTransactionId(String sellerTransactionId) {
        this.sellerTransactionId = sellerTransactionId;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public Payments orderId(String orderId) {
        this.setOrderId(orderId);
        return this;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPackageId() {
        return this.packageId;
    }

    public Payments packageId(String packageId) {
        this.setPackageId(packageId);
        return this;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public Double getAmount() {
        return this.amount;
    }

    public Payments amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getGame() {
        return this.game;
    }

    public Payments game(String game) {
        this.setGame(game);
        return this;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getStatus() {
        return this.status;
    }

    public Payments status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payments)) {
            return false;
        }
        return id != null && id.equals(((Payments) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payments{" +
            "id=" + getId() +
            ", buyerUserId='" + getBuyerUserId() + "'" +
            ", sellerUserId='" + getSellerUserId() + "'" +
            ", sellerRecieved='" + getSellerRecieved() + "'" +
            ", buyerTransactionId='" + getBuyerTransactionId() + "'" +
            ", sellerTransactionId='" + getSellerTransactionId() + "'" +
            ", orderId='" + getOrderId() + "'" +
            ", packageId='" + getPackageId() + "'" +
            ", amount=" + getAmount() +
            ", game='" + getGame() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
