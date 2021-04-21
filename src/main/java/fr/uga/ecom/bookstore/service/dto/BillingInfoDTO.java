package fr.uga.ecom.bookstore.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import fr.uga.ecom.bookstore.domain.enumeration.PaymentMethod;

/**
 * A DTO for the {@link fr.uga.ecom.bookstore.domain.BillingInfo} entity.
 */
public class BillingInfoDTO implements Serializable {
    
    private Long id;

    private String infoTitle;

    private String cardNumber;

    private LocalDate cardExpiryDate;

    private String cryptogram;

    private String email;

    @NotNull
    private PaymentMethod billingMethod;


    private Long customerId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfoTitle() {
        return infoTitle;
    }

    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(LocalDate cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    public String getCryptogram() {
        return cryptogram;
    }

    public void setCryptogram(String cryptogram) {
        this.cryptogram = cryptogram;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PaymentMethod getBillingMethod() {
        return billingMethod;
    }

    public void setBillingMethod(PaymentMethod billingMethod) {
        this.billingMethod = billingMethod;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customUserId) {
        this.customerId = customUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BillingInfoDTO)) {
            return false;
        }

        return id != null && id.equals(((BillingInfoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BillingInfoDTO{" +
            "id=" + getId() +
            ", infoTitle='" + getInfoTitle() + "'" +
            ", cardNumber='" + getCardNumber() + "'" +
            ", cardExpiryDate='" + getCardExpiryDate() + "'" +
            ", cryptogram='" + getCryptogram() + "'" +
            ", email='" + getEmail() + "'" +
            ", billingMethod='" + getBillingMethod() + "'" +
            ", customerId=" + getCustomerId() +
            "}";
    }
}
