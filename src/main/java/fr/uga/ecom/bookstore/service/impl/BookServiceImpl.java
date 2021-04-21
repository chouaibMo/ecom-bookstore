package fr.uga.ecom.bookstore.service.impl;

import fr.uga.ecom.bookstore.service.BookService;
import fr.uga.ecom.bookstore.domain.Book;
import fr.uga.ecom.bookstore.repository.BookRepository;
import fr.uga.ecom.bookstore.service.dto.BookDTO;
import fr.uga.ecom.bookstore.service.mapper.BookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Book}.
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDTO save(BookDTO bookDTO) {
        log.debug("Request to save Book : {}", bookDTO);
        Book book = bookMapper.toEntity(bookDTO);
        book = bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Books");
        return bookRepository.findAll(pageable)
            .map(bookMapper::toDto);
    }


    public Page<BookDTO> findAllWithEagerRelationships(Pageable pageable) {
        return bookRepository.findAllWithEagerRelationships(pageable).map(bookMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDTO> findOne(Long id) {
        log.debug("Request to get Book : {}", id);
        return bookRepository.findOneWithEagerRelationships(id)
            .map(bookMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Book : {}", id);
        bookRepository.deleteById(id);
    }


    /**
     * @author : CM
     * Implementation du service searchByQuery
     *
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BookDTO> searchByQuery(String query,Pageable pageable){
        log.debug("Request to (simple) search for a page of Books for query {}", query);
        return bookRepository.findByQuery(query, pageable)
            .map(bookMapper::toDto);
    }

    /**
     * @author : CM
     * Implementation du service findPopular
     *
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BookDTO> findMostRated(Pageable pageable){
        log.debug("Request to search for a page of popular Books");
        return bookRepository.findTop10ByOrderByRatingDesc(pageable)
            .map(bookMapper::toDto);
    }

    /**
     * @author : CM
     * Implementation du service findMostReviewed
     *
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BookDTO> findMostReviewed(Pageable pageable){
        log.debug("Request to search for a page of the most reviewed Books");
        return bookRepository.findMostReviewed(pageable)
            .map(bookMapper::toDto);
    }
}
