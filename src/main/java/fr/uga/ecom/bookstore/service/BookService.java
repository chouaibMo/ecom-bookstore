package fr.uga.ecom.bookstore.service;

import fr.uga.ecom.bookstore.service.dto.BookDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link fr.uga.ecom.bookstore.domain.Book}.
 */
public interface BookService {

    /**
     * Save a book.
     *
     * @param bookDTO the entity to save.
     * @return the persisted entity.
     */
    BookDTO save(BookDTO bookDTO);

    /**
     * Get all the books.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BookDTO> findAll(Pageable pageable);

    /**
     * Get all the books with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<BookDTO> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" book.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BookDTO> findOne(Long id);

    /**
     * Delete the "id" book.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);



    /**
     * @author : CM
     * Search for the book corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BookDTO> searchByQuery(String query,Pageable pageable);

    /**
     * @author : CM
     * Search for the 10 most popular books.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BookDTO> findMostRated(Pageable pageable);

    /**
     * @author : CM
     * Search for the 10 most reviewed books.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BookDTO> findMostReviewed(Pageable pageable);

}
