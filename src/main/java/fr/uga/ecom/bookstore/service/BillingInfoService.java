package fr.uga.ecom.bookstore.service;

import fr.uga.ecom.bookstore.service.dto.BillingInfoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.uga.ecom.bookstore.domain.BillingInfo}.
 */
public interface BillingInfoService {

    /**
     * Save a billingInfo.
     *
     * @param billingInfoDTO the entity to save.
     * @return the persisted entity.
     */
    BillingInfoDTO save(BillingInfoDTO billingInfoDTO);

    /**
     * Get all the billingInfos.
     *
     * @return the list of entities.
     */
    List<BillingInfoDTO> findAll();


    /**
     * Get the "id" billingInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BillingInfoDTO> findOne(Long id);

    /**
     * Delete the "id" billingInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
