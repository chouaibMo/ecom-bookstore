package fr.uga.ecom.bookstore.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link fr.uga.ecom.bookstore.domain.CustomUser} entity.
 */
public class CustomUserDTO implements Serializable {
    
    private Long id;

    private String phoneNumber;


    private Long userId;

    private Long cartId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long orderId) {
        this.cartId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomUserDTO)) {
            return false;
        }

        return id != null && id.equals(((CustomUserDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomUserDTO{" +
            "id=" + getId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", userId=" + getUserId() +
            ", cartId=" + getCartId() +
            "}";
    }
}
