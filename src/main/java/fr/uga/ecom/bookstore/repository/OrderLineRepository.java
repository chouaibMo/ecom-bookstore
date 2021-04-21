package fr.uga.ecom.bookstore.repository;

import fr.uga.ecom.bookstore.domain.OrderLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the OrderLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {

    /**
     * @author QCR
     * Récupérer les lignes de commandes par leur order id
     * @param orderid
     * @return
     */
    List<OrderLine> findAllByOrder_IdEquals(Long orderid);
}
