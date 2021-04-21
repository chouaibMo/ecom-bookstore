package fr.uga.ecom.bookstore.service.impl;

import fr.uga.ecom.bookstore.service.BookService;
import fr.uga.ecom.bookstore.service.CustomerCommentService;
import fr.uga.ecom.bookstore.domain.Book;
import fr.uga.ecom.bookstore.domain.CustomerComment;
import fr.uga.ecom.bookstore.repository.CustomerCommentRepository;
import fr.uga.ecom.bookstore.service.dto.BookDTO;
import fr.uga.ecom.bookstore.service.dto.CustomerCommentDTO;
import fr.uga.ecom.bookstore.service.mapper.CustomerCommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CustomerComment}.
 */
@Service
@Transactional
public class CustomerCommentServiceImpl implements CustomerCommentService {

    private final Logger log = LoggerFactory.getLogger(CustomerCommentServiceImpl.class);

    private final CustomerCommentRepository customerCommentRepository;

    private final CustomerCommentMapper customerCommentMapper;
    private final BookService bookService;

    public CustomerCommentServiceImpl(CustomerCommentRepository customerCommentRepository,
    		CustomerCommentMapper customerCommentMapper,
    		BookService bookService) {
        this.customerCommentRepository = customerCommentRepository;
        this.customerCommentMapper = customerCommentMapper;
        this.bookService = bookService;
    }

    @Override
    public CustomerCommentDTO save(CustomerCommentDTO customerCommentDTO) {
        log.debug("Request to save CustomerComment : {}", customerCommentDTO);
        CustomerComment customerComment = customerCommentMapper.toEntity(customerCommentDTO);
        customerComment = customerCommentRepository.save(customerComment);
        if( customerComment != null && customerCommentDTO.getBookId() > -1) {
        	log.debug("Request to save CustomerComment (1) : {}", customerCommentDTO);
        	Float f = this.findAvgRatingByBookId(customerCommentDTO.getBookId());
        	Optional<BookDTO> book = this.bookService.findOne(customerCommentDTO.getBookId());
        	log.debug("Request to save CustomerComment (2) : {}", customerCommentDTO);
        	if(book.isPresent()) {
        		log.debug("Request to save CustomerComment (3) : {}", customerCommentDTO);
        		BookDTO bookDTO = book.get();
        		log.debug("Request to save CustomerComment (4) : {}", customerCommentDTO);
        		bookDTO.setRating(f);
        		log.debug("Request to save CustomerComment (5) : {}", customerCommentDTO);
        		this.bookService.save(bookDTO);
        	}
        }
        return customerCommentMapper.toDto(customerComment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerCommentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerComments");
        return customerCommentRepository.findAll(pageable)
            .map(customerCommentMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerCommentDTO> findOne(Long id) {
        log.debug("Request to get CustomerComment : {}", id);
        return customerCommentRepository.findById(id)
            .map(customerCommentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerComment : {}", id);
        customerCommentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerCommentDTO> findOneByCustomerIdByBookId(Long customerId, Long bookId){
        log.debug("Request to get CustomerComment by Customer and Book : {} , {}", customerId, bookId);
        return customerCommentRepository.findOneByCustomer_IdEqualsAndBook_IdEquals(customerId, bookId).map(customerCommentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Float findAvgRatingByBookId(Long bookId){
        return customerCommentRepository.avgRatingByBook_Id(bookId);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<CustomerCommentDTO> findAllByBookId(Long bookId, Pageable pageable){
        log.debug("Request to get All CustomerComment by Book : {}", bookId);
        return customerCommentRepository.findByBook_Id(bookId,pageable)
            .map(customerCommentMapper::toDto);
    }
}
