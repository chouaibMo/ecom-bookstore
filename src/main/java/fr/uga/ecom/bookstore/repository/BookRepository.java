package fr.uga.ecom.bookstore.repository;

import fr.uga.ecom.bookstore.domain.Book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Book entity.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    @Query(value = "select distinct book from Book book left join fetch book.authors",
        countQuery = "select count(distinct book) from Book book")
    Page<Book> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct book from Book book left join fetch book.authors")
    List<Book> findAllWithEagerRelationships();

    @Query("select book from Book book left join fetch book.authors where book.id =:id")
    Optional<Book> findOneWithEagerRelationships(@Param("id") Long id);

    /**
     * @author CM
     * Search for the book corresponding to the query.
     */
    @Query(value = "select book from Book book where LOWER(book.title) like LOWER(concat('%', concat(?1, '%'))) or LOWER(book.otherDetails) like LOWER(concat('%', concat(?1, '%')))",
        countQuery = "select count(distinct book) from Book book where LOWER(book.title) like LOWER(concat('%', concat(?1, '%'))) or LOWER(book.otherDetails) like LOWER(concat('%', concat(?1, '%')))")
    Page<Book> findByQuery(@Param("query") String query, Pageable pageable);

    /**
     * @author CM
     * Search for the 10 most rated books.
     *
     * Query is created from the method name (top10, order by .. desc)
     */
    Page<Book> findTop10ByOrderByRatingDesc(Pageable pageable);

    /**
     * @author CM
     * Search for the 10 most reviewed books.
     */
    @Query( value = "select book.* from (select book_id, count(*) nbComments from customer_comment group by book_id order by nbComments desc limit 10) comments left join Book book on comments.book_id = book.id",
        countQuery = "select count(distinct book) from Book book",
        nativeQuery = true)
    Page<Book> findMostReviewed(Pageable pageable);
}
