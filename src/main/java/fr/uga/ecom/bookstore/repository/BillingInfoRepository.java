package fr.uga.ecom.bookstore.repository;

import fr.uga.ecom.bookstore.domain.BillingInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BillingInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillingInfoRepository extends JpaRepository<BillingInfo, Long> {
}
