package fr.uga.ecom.bookstore.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import fr.uga.ecom.bookstore.domain.enumeration.Country;

/**
 * A DTO for the {@link fr.uga.ecom.bookstore.domain.Address} entity.
 */
public class AddressDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String address;

    @NotNull
    private String city;

    @NotNull
    private String zipCode;

    private Country country;


    private Long customerId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
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
        if (!(o instanceof AddressDTO)) {
            return false;
        }

        return id != null && id.equals(((AddressDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddressDTO{" +
            "id=" + getId() +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", country='" + getCountry() + "'" +
            ", customerId=" + getCustomerId() +
            "}";
    }
}
