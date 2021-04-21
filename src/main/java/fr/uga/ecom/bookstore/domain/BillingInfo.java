package fr.uga.ecom.bookstore.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import fr.uga.ecom.bookstore.domain.enumeration.PaymentMethod;

/**
 * A BillingInfo.
 */
@Entity
@Table(name = "billing_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BillingInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "info_title")
    private String infoTitle;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "card_expiry_date")
    private LocalDate cardExpiryDate;

    @Column(name = "cryptogram")
    private String cryptogram;

    @Column(name = "email")
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "billing_method", nullable = false)
    private PaymentMethod billingMethod;

    @ManyToOne
    @JsonIgnoreProperties(value = "billingInfos", allowSetters = true)
    private CustomUser customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfoTitle() {
        return infoTitle;
    }

    public BillingInfo infoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
        return this;
    }

    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public BillingInfo cardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getCardExpiryDate() {
        return cardExpiryDate;
    }

    public BillingInfo cardExpiryDate(LocalDate cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
        return this;
    }

    public void setCardExpiryDate(LocalDate cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    public String getCryptogram() {
        return cryptogram;
    }

    public BillingInfo cryptogram(String cryptogram) {
        this.cryptogram = cryptogram;
        return this;
    }

    public void setCryptogram(String cryptogram) {
        this.cryptogram = cryptogram;
    }

    public String getEmail() {
        return email;
    }

    public BillingInfo email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PaymentMethod getBillingMethod() {
        return billingMethod;
    }

    public BillingInfo billingMethod(PaymentMethod billingMethod) {
        this.billingMethod = billingMethod;
        return this;
    }

    public void setBillingMethod(PaymentMethod billingMethod) {
        this.billingMethod = billingMethod;
    }

    public CustomUser getCustomer() {
        return customer;
    }

    public BillingInfo customer(CustomUser customUser) {
        this.customer = customUser;
        return this;
    }

    public void setCustomer(CustomUser customUser) {
        this.customer = customUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BillingInfo)) {
            return false;
        }
        return id != null && id.equals(((BillingInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BillingInfo{" +
            "id=" + getId() +
            ", infoTitle='" + getInfoTitle() + "'" +
            ", cardNumber='" + getCardNumber() + "'" +
            ", cardExpiryDate='" + getCardExpiryDate() + "'" +
            ", cryptogram='" + getCryptogram() + "'" +
            ", email='" + getEmail() + "'" +
            ", billingMethod='" + getBillingMethod() + "'" +
            "}";
    }
}
