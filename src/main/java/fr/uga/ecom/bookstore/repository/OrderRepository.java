package fr.uga.ecom.bookstore.repository;

import fr.uga.ecom.bookstore.domain.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Order entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * @author : Emez
     */
    @Query("SELECT ord from Order ord where ord.customer.id = :userId and ord.orderStatus = 'IN_CART' ")
    Order getUserCart(@Param("userId") Long userId);


    /**
     * @author QCR
     *
     */
    @Query("SELECT ord FROM Order ord WHERE ord.customer.id = :customUserId AND ord.orderStatus <> 'IN_CART' ORDER BY ord.orderDate DESC ")
    Page<Order> findOrderHistory(@Param("customUserId") Long customUserId, Pageable pageable);
}
