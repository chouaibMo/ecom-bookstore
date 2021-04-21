package fr.uga.ecom.bookstore.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CustomUser.
 */
@Entity
@Table(name = "custom_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToOne
    @JoinColumn(unique = true)
    private Order cart;

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<BillingInfo> billingInfos = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Address> addresses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public CustomUser phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User getUser() {
        return user;
    }

    public CustomUser user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Order getCart() {
        return cart;
    }

    public CustomUser cart(Order order) {
        this.cart = order;
        return this;
    }

    public void setCart(Order order) {
        this.cart = order;
    }

    public Set<BillingInfo> getBillingInfos() {
        return billingInfos;
    }

    public CustomUser billingInfos(Set<BillingInfo> billingInfos) {
        this.billingInfos = billingInfos;
        return this;
    }

    public CustomUser addBillingInfo(BillingInfo billingInfo) {
        this.billingInfos.add(billingInfo);
        billingInfo.setCustomer(this);
        return this;
    }

    public CustomUser removeBillingInfo(BillingInfo billingInfo) {
        this.billingInfos.remove(billingInfo);
        billingInfo.setCustomer(null);
        return this;
    }

    public void setBillingInfos(Set<BillingInfo> billingInfos) {
        this.billingInfos = billingInfos;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public CustomUser addresses(Set<Address> addresses) {
        this.addresses = addresses;
        return this;
    }

    public CustomUser addAddress(Address address) {
        this.addresses.add(address);
        address.setCustomer(this);
        return this;
    }

    public CustomUser removeAddress(Address address) {
        this.addresses.remove(address);
        address.setCustomer(null);
        return this;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomUser)) {
            return false;
        }
        return id != null && id.equals(((CustomUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomUser{" +
            "id=" + getId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
