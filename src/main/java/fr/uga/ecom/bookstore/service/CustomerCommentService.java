package fr.uga.ecom.bookstore.service;

import fr.uga.ecom.bookstore.service.dto.CustomerCommentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link fr.uga.ecom.bookstore.domain.CustomerComment}.
 */
public interface CustomerCommentService {

    /**
     * Save a customerComment.
     *
     * @param customerCommentDTO the entity to save.
     * @return the persisted entity.
     */
    CustomerCommentDTO save(CustomerCommentDTO customerCommentDTO);

    /**
     * Get all the customerComments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CustomerCommentDTO> findAll(Pageable pageable);


    /**
     * Get the "id" customerComment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomerCommentDTO> findOne(Long id);

    /**
     * Delete the "id" customerComment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);


    /**
     * Find a comment by customerId and bookId
     *
     * @param customerId the id of the customer
     * @param  bookId the id of the book.
     */
     Optional<CustomerCommentDTO> findOneByCustomerIdByBookId(Long customerId, Long bookId);

    /**
     * Find the average rating for a book
     *
     * @param  bookId the id of the book.
     */
     Float findAvgRatingByBookId(Long bookId);

    /**
     * @author CM
     * Get all reviews corresponding to a book
     *
     * @param  bookId the id of the book.
     */
    Page<CustomerCommentDTO> findAllByBookId(Long bookId, Pageable pageable);
}
