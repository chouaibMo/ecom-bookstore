package fr.uga.ecom.bookstore.web.rest;

import fr.uga.ecom.bookstore.BookstoreApp;
import fr.uga.ecom.bookstore.domain.CustomerComment;
import fr.uga.ecom.bookstore.repository.CustomerCommentRepository;
import fr.uga.ecom.bookstore.service.CustomerCommentService;
import fr.uga.ecom.bookstore.service.dto.CustomerCommentDTO;
import fr.uga.ecom.bookstore.service.mapper.CustomerCommentMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static fr.uga.ecom.bookstore.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CustomerCommentResource} REST controller.
 */
@SpringBootTest(classes = BookstoreApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustomerCommentResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_COMMENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_COMMENT_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Float DEFAULT_RATING = 1F;
    private static final Float UPDATED_RATING = 2F;

    @Autowired
    private CustomerCommentRepository customerCommentRepository;

    @Autowired
    private CustomerCommentMapper customerCommentMapper;

    @Autowired
    private CustomerCommentService customerCommentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerCommentMockMvc;

    private CustomerComment customerComment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerComment createEntity(EntityManager em) {
        CustomerComment customerComment = new CustomerComment()
            .title(DEFAULT_TITLE)
            .comment(DEFAULT_COMMENT)
            .commentDate(DEFAULT_COMMENT_DATE)
            .rating(DEFAULT_RATING);
        return customerComment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerComment createUpdatedEntity(EntityManager em) {
        CustomerComment customerComment = new CustomerComment()
            .title(UPDATED_TITLE)
            .comment(UPDATED_COMMENT)
            .commentDate(UPDATED_COMMENT_DATE)
            .rating(UPDATED_RATING);
        return customerComment;
    }

    @BeforeEach
    public void initTest() {
        customerComment = createEntity(em);
    }
/*
    @Test
    @Transactional
    public void createCustomerComment() throws Exception {
        int databaseSizeBeforeCreate = customerCommentRepository.findAll().size();
        // Create the CustomerComment
        CustomerCommentDTO customerCommentDTO = customerCommentMapper.toDto(customerComment);
        restCustomerCommentMockMvc.perform(post("/api/customer-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerCommentDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerComment in the database
        List<CustomerComment> customerCommentList = customerCommentRepository.findAll();
        assertThat(customerCommentList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerComment testCustomerComment = customerCommentList.get(customerCommentList.size() - 1);
        assertThat(testCustomerComment.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCustomerComment.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testCustomerComment.getCommentDate()).isEqualTo(DEFAULT_COMMENT_DATE);
        assertThat(testCustomerComment.getRating()).isEqualTo(DEFAULT_RATING);
    }
*/
    @Test
    @Transactional
    public void createCustomerCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerCommentRepository.findAll().size();

        // Create the CustomerComment with an existing ID
        customerComment.setId(1L);
        CustomerCommentDTO customerCommentDTO = customerCommentMapper.toDto(customerComment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerCommentMockMvc.perform(post("/api/customer-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerCommentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerComment in the database
        List<CustomerComment> customerCommentList = customerCommentRepository.findAll();
        assertThat(customerCommentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCommentDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerCommentRepository.findAll().size();
        // set the field null
        customerComment.setCommentDate(null);

        // Create the CustomerComment, which fails.
        CustomerCommentDTO customerCommentDTO = customerCommentMapper.toDto(customerComment);


        restCustomerCommentMockMvc.perform(post("/api/customer-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerCommentDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerComment> customerCommentList = customerCommentRepository.findAll();
        assertThat(customerCommentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRatingIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerCommentRepository.findAll().size();
        // set the field null
        customerComment.setRating(null);

        // Create the CustomerComment, which fails.
        CustomerCommentDTO customerCommentDTO = customerCommentMapper.toDto(customerComment);


        restCustomerCommentMockMvc.perform(post("/api/customer-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerCommentDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerComment> customerCommentList = customerCommentRepository.findAll();
        assertThat(customerCommentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerComments() throws Exception {
        // Initialize the database
        customerCommentRepository.saveAndFlush(customerComment);

        // Get all the customerCommentList
        restCustomerCommentMockMvc.perform(get("/api/customer-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].commentDate").value(hasItem(sameInstant(DEFAULT_COMMENT_DATE))))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.doubleValue())));
    }

    @Test
    @Transactional
    public void getCustomerComment() throws Exception {
        // Initialize the database
        customerCommentRepository.saveAndFlush(customerComment);

        // Get the customerComment
        restCustomerCommentMockMvc.perform(get("/api/customer-comments/{id}", customerComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerComment.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.commentDate").value(sameInstant(DEFAULT_COMMENT_DATE)))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCustomerComment() throws Exception {
        // Get the customerComment
        restCustomerCommentMockMvc.perform(get("/api/customer-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }
/*
    @Test
    @Transactional
    public void updateCustomerComment() throws Exception {
        // Initialize the database
        customerCommentRepository.saveAndFlush(customerComment);

        int databaseSizeBeforeUpdate = customerCommentRepository.findAll().size();

        // Update the customerComment
        CustomerComment updatedCustomerComment = customerCommentRepository.findById(customerComment.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerComment are not directly saved in db
        em.detach(updatedCustomerComment);
        updatedCustomerComment
            .title(UPDATED_TITLE)
            .comment(UPDATED_COMMENT)
            .commentDate(UPDATED_COMMENT_DATE)
            .rating(UPDATED_RATING);
        CustomerCommentDTO customerCommentDTO = customerCommentMapper.toDto(updatedCustomerComment);

        restCustomerCommentMockMvc.perform(put("/api/customer-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerCommentDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerComment in the database
        List<CustomerComment> customerCommentList = customerCommentRepository.findAll();
        assertThat(customerCommentList).hasSize(databaseSizeBeforeUpdate);
        CustomerComment testCustomerComment = customerCommentList.get(customerCommentList.size() - 1);
        assertThat(testCustomerComment.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCustomerComment.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testCustomerComment.getCommentDate()).isEqualTo(UPDATED_COMMENT_DATE);
        assertThat(testCustomerComment.getRating()).isEqualTo(UPDATED_RATING);
    }
*/
    @Test
    @Transactional
    public void updateNonExistingCustomerComment() throws Exception {
        int databaseSizeBeforeUpdate = customerCommentRepository.findAll().size();

        // Create the CustomerComment
        CustomerCommentDTO customerCommentDTO = customerCommentMapper.toDto(customerComment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerCommentMockMvc.perform(put("/api/customer-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerCommentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerComment in the database
        List<CustomerComment> customerCommentList = customerCommentRepository.findAll();
        assertThat(customerCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerComment() throws Exception {
        // Initialize the database
        customerCommentRepository.saveAndFlush(customerComment);

        int databaseSizeBeforeDelete = customerCommentRepository.findAll().size();

        // Delete the customerComment
        restCustomerCommentMockMvc.perform(delete("/api/customer-comments/{id}", customerComment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerComment> customerCommentList = customerCommentRepository.findAll();
        assertThat(customerCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
