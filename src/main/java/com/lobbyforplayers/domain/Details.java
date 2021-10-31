package com.lobbyforplayers.domain;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * A Details.
 */
@Document(collection = "DETAILS")
// @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Details implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    private String loginName;

    @NotNull
    private String password;

    private String lastName;

    private String firstName;

    private String securtiyQuestion;

    private String securityAnswer;

    private String parentalPassword;

    private String firstCdKey;

    private String otherInformation;

    @NotNull
    private Instant enteredDate;

    private LocalDate orderDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Details id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public Details loginName(String loginName) {
        this.setLoginName(loginName);
        return this;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return this.password;
    }

    public Details password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Details lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Details firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecurtiyQuestion() {
        return this.securtiyQuestion;
    }

    public Details securtiyQuestion(String securtiyQuestion) {
        this.setSecurtiyQuestion(securtiyQuestion);
        return this;
    }

    public void setSecurtiyQuestion(String securtiyQuestion) {
        this.securtiyQuestion = securtiyQuestion;
    }

    public String getSecurityAnswer() {
        return this.securityAnswer;
    }

    public Details securityAnswer(String securityAnswer) {
        this.setSecurityAnswer(securityAnswer);
        return this;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getParentalPassword() {
        return this.parentalPassword;
    }

    public Details parentalPassword(String parentalPassword) {
        this.setParentalPassword(parentalPassword);
        return this;
    }

    public void setParentalPassword(String parentalPassword) {
        this.parentalPassword = parentalPassword;
    }

    public String getFirstCdKey() {
        return this.firstCdKey;
    }

    public Details firstCdKey(String firstCdKey) {
        this.setFirstCdKey(firstCdKey);
        return this;
    }

    public void setFirstCdKey(String firstCdKey) {
        this.firstCdKey = firstCdKey;
    }

    public String getOtherInformation() {
        return this.otherInformation;
    }

    public Details otherInformation(String otherInformation) {
        this.setOtherInformation(otherInformation);
        return this;
    }

    public void setOtherInformation(String otherInformation) {
        this.otherInformation = otherInformation;
    }

    public Instant getEnteredDate() {
        return this.enteredDate;
    }

    public Details enteredDate(Instant enteredDate) {
        this.setEnteredDate(enteredDate);
        return this;
    }

    public void setEnteredDate(Instant enteredDate) {
        this.enteredDate = enteredDate;
    }

    public LocalDate getOrderDate() {
        return this.orderDate;
    }

    public Details orderDate(LocalDate orderDate) {
        this.setOrderDate(orderDate);
        return this;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Details)) {
            return false;
        }
        return id != null && id.equals(((Details) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Details{" +
            "id=" + getId() +
            ", loginName='" + getLoginName() + "'" +
            ", password='" + getPassword() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", securtiyQuestion='" + getSecurtiyQuestion() + "'" +
            ", securityAnswer='" + getSecurityAnswer() + "'" +
            ", parentalPassword='" + getParentalPassword() + "'" +
            ", firstCdKey='" + getFirstCdKey() + "'" +
            ", otherInformation='" + getOtherInformation() + "'" +
            ", enteredDate='" + getEnteredDate() + "'" +
            ", orderDate='" + getOrderDate() + "'" +
            "}";
    }
}
