package fr.uga.ecom.bookstore.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import fr.uga.ecom.bookstore.domain.enumeration.OrderStatus;

/**
 * A DTO for the {@link fr.uga.ecom.bookstore.domain.Order} entity.
 */
public class OrderDTO implements Serializable {
    
    private Long id;

    @NotNull
    private OrderStatus orderStatus;

    private String orderDetails;

    @NotNull
    private Float totalPrice;

    private ZonedDateTime orderDate;

    private ZonedDateTime paymentDate;


    private Long customerId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ZonedDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public ZonedDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(ZonedDateTime paymentDate) {
        this.paymentDate = paymentDate;
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
        if (!(o instanceof OrderDTO)) {
            return false;
        }

        return id != null && id.equals(((OrderDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderDTO{" +
            "id=" + getId() +
            ", orderStatus='" + getOrderStatus() + "'" +
            ", orderDetails='" + getOrderDetails() + "'" +
            ", totalPrice=" + getTotalPrice() +
            ", orderDate='" + getOrderDate() + "'" +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", customerId=" + getCustomerId() +
            "}";
    }
}
