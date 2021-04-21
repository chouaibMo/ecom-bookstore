package fr.uga.ecom.bookstore.web.rest;

import fr.uga.ecom.bookstore.service.CustomerCommentService;
import fr.uga.ecom.bookstore.web.rest.errors.BadRequestAlertException;
import fr.uga.ecom.bookstore.service.dto.CustomerCommentDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.uga.ecom.bookstore.domain.CustomerComment}.
 */
@RestController
@RequestMapping("/api")
public class CustomerCommentResource {

    private final Logger log = LoggerFactory.getLogger(CustomerCommentResource.class);

    private static final String ENTITY_NAME = "customerComment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerCommentService customerCommentService;

    public CustomerCommentResource(CustomerCommentService customerCommentService) {
        this.customerCommentService = customerCommentService;
    }

    /**
     * {@code POST  /customer-comments} : Create a new customerComment.
     *
     * @param customerCommentDTO the customerCommentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerCommentDTO, or with status {@code 400 (Bad Request)} if the customerComment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-comments")
    public ResponseEntity<CustomerCommentDTO> createCustomerComment(@Valid @RequestBody CustomerCommentDTO customerCommentDTO) throws URISyntaxException {
        log.debug("REST request to save CustomerComment : {}", customerCommentDTO);
        if (customerCommentDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerCommentDTO result = customerCommentService.save(customerCommentDTO);
        return ResponseEntity.created(new URI("/api/customer-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-comments} : Updates an existing customerComment.
     *
     * @param customerCommentDTO the customerCommentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerCommentDTO,
     * or with status {@code 400 (Bad Request)} if the customerCommentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerCommentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-comments")
    public ResponseEntity<CustomerCommentDTO> updateCustomerComment(@Valid @RequestBody CustomerCommentDTO customerCommentDTO) throws URISyntaxException {
        log.debug("REST request to update CustomerComment : {}", customerCommentDTO);
        if (customerCommentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerCommentDTO result = customerCommentService.save(customerCommentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerCommentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-comments} : get all the customerComments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerComments in body.
     */
    @GetMapping("/customer-comments")
    public ResponseEntity<List<CustomerCommentDTO>> getAllCustomerComments(Pageable pageable) {
        log.debug("REST request to get a page of CustomerComments");
        Page<CustomerCommentDTO> page = customerCommentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /customer-comments/:id} : get the "id" customerComment.
     *
     * @param id the id of the customerCommentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerCommentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-comments/{id}")
    public ResponseEntity<CustomerCommentDTO> getCustomerComment(@PathVariable Long id) {
        log.debug("REST request to get CustomerComment : {}", id);
        Optional<CustomerCommentDTO> customerCommentDTO = customerCommentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerCommentDTO);
    }

    /**
     * {@code DELETE  /customer-comments/:id} : delete the "id" customerComment.
     *
     * @param id the id of the customerCommentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-comments/{id}")
    public ResponseEntity<Void> deleteCustomerComment(@PathVariable Long id) {
        log.debug("REST request to delete CustomerComment : {}", id);
        customerCommentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * @author QCR
     * {@code GET  /user/:customerId/book/:bookId/customer-comment} : get the "id" customerComment.
     *
     * @param customerId the id of the customerCommentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerCommentDTO, or with status {@code 404 (Not Found)}.

     */
    @GetMapping("/user/{customerId}/book/{bookId}/customer-comment")
    public ResponseEntity<CustomerCommentDTO> getCustomerComment(@PathVariable Long customerId,
                                                                 @PathVariable Long bookId) {
        log.debug("REST request to get CustomerComment by Customer and Book Id : {}", bookId);


        //QCR: TMP
        Float avgRating = customerCommentService.findAvgRatingByBookId(bookId);
        log.debug("Find avgRating : {}", avgRating);

        Optional<CustomerCommentDTO> customerCommentDTO =
            customerCommentService.findOneByCustomerIdByBookId(customerId, bookId);
        if(!customerCommentDTO.isPresent()){
            CustomerCommentDTO newComment = new CustomerCommentDTO();
            return ResponseUtil.wrapOrNotFound(customerCommentDTO.of(newComment));
        }else{
            return ResponseUtil.wrapOrNotFound(customerCommentDTO);
        }
    }

    /**
     * @author CM
     * {@code GET  /customer-comments/book/:id} : get all the customerComments of the book "id".
     *
     * @param bookId the id of the book
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerComments in body.
     */
    @GetMapping("/customer-comments/book/{bookId}")
    public ResponseEntity<List<CustomerCommentDTO>> getAllCustomerCommentsByBook(@PathVariable Long bookId, Pageable pageable) {
        log.debug("REST request to get a page of CustomerComments corresponding to book {}", bookId);
        Page<CustomerCommentDTO> page = customerCommentService.findAllByBookId(bookId,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
