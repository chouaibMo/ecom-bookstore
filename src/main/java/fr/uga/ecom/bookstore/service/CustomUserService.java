package fr.uga.ecom.bookstore.service;

import fr.uga.ecom.bookstore.service.dto.CustomUserDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.uga.ecom.bookstore.domain.CustomUser}.
 */
public interface CustomUserService {

    /**
     * Save a customUser.
     *
     * @param customUserDTO the entity to save.
     * @return the persisted entity.
     */
    CustomUserDTO save(CustomUserDTO customUserDTO);

    /**
     * Get all the customUsers.
     *
     * @return the list of entities.
     */
    List<CustomUserDTO> findAll();


    /**
     * Get the "id" customUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomUserDTO> findOne(Long id);

    /**
     * Delete the "id" customUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get the customUser corresponding to jhipster user id
     *
     * @param id the id of the jhipster user.
     * @return the entity.
     */
    Optional<CustomUserDTO> findByJID(Long id);


    /**
     * Get the username from Custom User Id
     *
     * @param id the id of the custom user.
     * @return username
     */
    public Optional<String> findUsernameById(Long id);
}
