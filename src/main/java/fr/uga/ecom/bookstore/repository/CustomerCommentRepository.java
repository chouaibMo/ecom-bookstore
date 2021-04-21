package fr.uga.ecom.bookstore.repository;

import fr.uga.ecom.bookstore.domain.CustomerComment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Spring Data  repository for the CustomerComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerCommentRepository extends JpaRepository<CustomerComment, Long> {
    /**
     * @author QCR
     * Récupérer un comment a paritr de l'id customer et du book id
     * @param customerId : L'id du customer
     * @param bookId : L'id du book
     * @return
     */
    Optional<CustomerComment> findOneByCustomer_IdEqualsAndBook_IdEquals(Long customerId, Long bookId);

    /**
     *  @author QCR
     *  Récupération de la note moyenne pour un livre
     *  @param bookId : L'id du book
     * @return note moyenne (0 ~ 5)
     */
    @Query(value= "SELECT AVG(rating) FROM CustomerComment WHERE book_id= ?1")
    Float avgRatingByBook_Id(Long bookId);

    /**
     * @author CM
     * Get all reviews corresponding to a book
     *
     */
    Page<CustomerComment> findByBook_Id(Long bookId, Pageable pageable);
}
