package fr.uga.ecom.bookstore.web.rest;

import fr.uga.ecom.bookstore.BookstoreApp;
import fr.uga.ecom.bookstore.domain.Book;
import fr.uga.ecom.bookstore.domain.Category;
import fr.uga.ecom.bookstore.domain.Author;
import fr.uga.ecom.bookstore.domain.Discount;
import fr.uga.ecom.bookstore.repository.BookRepository;
import fr.uga.ecom.bookstore.service.BookService;
import fr.uga.ecom.bookstore.service.dto.BookDTO;
import fr.uga.ecom.bookstore.service.mapper.BookMapper;
import fr.uga.ecom.bookstore.service.dto.BookCriteria;
import fr.uga.ecom.bookstore.service.BookQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.uga.ecom.bookstore.domain.enumeration.Language;
import fr.uga.ecom.bookstore.domain.enumeration.BookType;
/**
 * Integration tests for the {@link BookResource} REST controller.
 */
@SpringBootTest(classes = BookstoreApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class BookResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;
    private static final Float SMALLER_PRICE = 1F - 1F;

    private static final Float DEFAULT_RATING = 1F;
    private static final Float UPDATED_RATING = 2F;
    private static final Float SMALLER_RATING = 1F - 1F;

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.FR;
    private static final Language UPDATED_LANGUAGE = Language.EN;

    private static final BookType DEFAULT_FORMAT = BookType.AUDIO;
    private static final BookType UPDATED_FORMAT = BookType.EBOOK;

    private static final Long DEFAULT_PAPER_BACK_QUANTITY = 1L;
    private static final Long UPDATED_PAPER_BACK_QUANTITY = 2L;
    private static final Long SMALLER_PAPER_BACK_QUANTITY = 1L - 1L;

    private static final LocalDate DEFAULT_PUBLICATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PUBLICATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_PUBLICATION_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_ISBN = "AAAAAAAAAA";
    private static final String UPDATED_ISBN = "BBBBBBBBBB";

    private static final Integer DEFAULT_PAGES = 1;
    private static final Integer UPDATED_PAGES = 2;
    private static final Integer SMALLER_PAGES = 1 - 1;

    private static final String DEFAULT_OTHER_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_DETAILS = "BBBBBBBBBB";

    @Autowired
    private BookRepository bookRepository;

    @Mock
    private BookRepository bookRepositoryMock;

    @Autowired
    private BookMapper bookMapper;

    @Mock
    private BookService bookServiceMock;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookQueryService bookQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBookMockMvc;

    private Book book;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Book createEntity(EntityManager em) {
        Book book = new Book()
            .title(DEFAULT_TITLE)
            .price(DEFAULT_PRICE)
            .rating(DEFAULT_RATING)
            .imageURL(DEFAULT_IMAGE_URL)
            .language(DEFAULT_LANGUAGE)
            .format(DEFAULT_FORMAT)
            .paperBackQuantity(DEFAULT_PAPER_BACK_QUANTITY)
            .publicationDate(DEFAULT_PUBLICATION_DATE)
            .isbn(DEFAULT_ISBN)
            .pages(DEFAULT_PAGES)
            .otherDetails(DEFAULT_OTHER_DETAILS);
        return book;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Book createUpdatedEntity(EntityManager em) {
        Book book = new Book()
            .title(UPDATED_TITLE)
            .price(UPDATED_PRICE)
            .rating(UPDATED_RATING)
            .imageURL(UPDATED_IMAGE_URL)
            .language(UPDATED_LANGUAGE)
            .format(UPDATED_FORMAT)
            .paperBackQuantity(UPDATED_PAPER_BACK_QUANTITY)
            .publicationDate(UPDATED_PUBLICATION_DATE)
            .isbn(UPDATED_ISBN)
            .pages(UPDATED_PAGES)
            .otherDetails(UPDATED_OTHER_DETAILS);
        return book;
    }

    @BeforeEach
    public void initTest() {
        book = createEntity(em);
    }

    @Test
    @Transactional
    public void createBook() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();
        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);
        restBookMockMvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isCreated());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate + 1);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBook.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testBook.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testBook.getImageURL()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testBook.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testBook.getFormat()).isEqualTo(DEFAULT_FORMAT);
        assertThat(testBook.getPaperBackQuantity()).isEqualTo(DEFAULT_PAPER_BACK_QUANTITY);
        assertThat(testBook.getPublicationDate()).isEqualTo(DEFAULT_PUBLICATION_DATE);
        assertThat(testBook.getIsbn()).isEqualTo(DEFAULT_ISBN);
        assertThat(testBook.getPages()).isEqualTo(DEFAULT_PAGES);
        assertThat(testBook.getOtherDetails()).isEqualTo(DEFAULT_OTHER_DETAILS);
    }

    @Test
    @Transactional
    public void createBookWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();

        // Create the Book with an existing ID
        book.setId(1L);
        BookDTO bookDTO = bookMapper.toDto(book);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookMockMvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setTitle(null);

        // Create the Book, which fails.
        BookDTO bookDTO = bookMapper.toDto(book);


        restBookMockMvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isBadRequest());

        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setPrice(null);

        // Create the Book, which fails.
        BookDTO bookDTO = bookMapper.toDto(book);


        restBookMockMvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isBadRequest());

        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFormatIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setFormat(null);

        // Create the Book, which fails.
        BookDTO bookDTO = bookMapper.toDto(book);


        restBookMockMvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isBadRequest());

        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPublicationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setPublicationDate(null);

        // Create the Book, which fails.
        BookDTO bookDTO = bookMapper.toDto(book);


        restBookMockMvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isBadRequest());

        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBooks() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList
        restBookMockMvc.perform(get("/api/books?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(book.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].imageURL").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].format").value(hasItem(DEFAULT_FORMAT.toString())))
            .andExpect(jsonPath("$.[*].paperBackQuantity").value(hasItem(DEFAULT_PAPER_BACK_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].publicationDate").value(hasItem(DEFAULT_PUBLICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].isbn").value(hasItem(DEFAULT_ISBN)))
            .andExpect(jsonPath("$.[*].pages").value(hasItem(DEFAULT_PAGES)))
            .andExpect(jsonPath("$.[*].otherDetails").value(hasItem(DEFAULT_OTHER_DETAILS)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllBooksWithEagerRelationshipsIsEnabled() throws Exception {
        when(bookServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBookMockMvc.perform(get("/api/books?eagerload=true"))
            .andExpect(status().isOk());

        verify(bookServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllBooksWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(bookServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBookMockMvc.perform(get("/api/books?eagerload=true"))
            .andExpect(status().isOk());

        verify(bookServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get the book
        restBookMockMvc.perform(get("/api/books/{id}", book.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(book.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.doubleValue()))
            .andExpect(jsonPath("$.imageURL").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()))
            .andExpect(jsonPath("$.format").value(DEFAULT_FORMAT.toString()))
            .andExpect(jsonPath("$.paperBackQuantity").value(DEFAULT_PAPER_BACK_QUANTITY.intValue()))
            .andExpect(jsonPath("$.publicationDate").value(DEFAULT_PUBLICATION_DATE.toString()))
            .andExpect(jsonPath("$.isbn").value(DEFAULT_ISBN))
            .andExpect(jsonPath("$.pages").value(DEFAULT_PAGES))
            .andExpect(jsonPath("$.otherDetails").value(DEFAULT_OTHER_DETAILS));
    }


    @Test
    @Transactional
    public void getBooksByIdFiltering() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        Long id = book.getId();

        defaultBookShouldBeFound("id.equals=" + id);
        defaultBookShouldNotBeFound("id.notEquals=" + id);

        defaultBookShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBookShouldNotBeFound("id.greaterThan=" + id);

        defaultBookShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBookShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBooksByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title equals to DEFAULT_TITLE
        defaultBookShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the bookList where title equals to UPDATED_TITLE
        defaultBookShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllBooksByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title not equals to DEFAULT_TITLE
        defaultBookShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the bookList where title not equals to UPDATED_TITLE
        defaultBookShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllBooksByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultBookShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the bookList where title equals to UPDATED_TITLE
        defaultBookShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllBooksByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title is not null
        defaultBookShouldBeFound("title.specified=true");

        // Get all the bookList where title is null
        defaultBookShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllBooksByTitleContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title contains DEFAULT_TITLE
        defaultBookShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the bookList where title contains UPDATED_TITLE
        defaultBookShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllBooksByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title does not contain DEFAULT_TITLE
        defaultBookShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the bookList where title does not contain UPDATED_TITLE
        defaultBookShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllBooksByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where price equals to DEFAULT_PRICE
        defaultBookShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the bookList where price equals to UPDATED_PRICE
        defaultBookShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllBooksByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where price not equals to DEFAULT_PRICE
        defaultBookShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the bookList where price not equals to UPDATED_PRICE
        defaultBookShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllBooksByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultBookShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the bookList where price equals to UPDATED_PRICE
        defaultBookShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllBooksByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where price is not null
        defaultBookShouldBeFound("price.specified=true");

        // Get all the bookList where price is null
        defaultBookShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where price is greater than or equal to DEFAULT_PRICE
        defaultBookShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the bookList where price is greater than or equal to UPDATED_PRICE
        defaultBookShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllBooksByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where price is less than or equal to DEFAULT_PRICE
        defaultBookShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the bookList where price is less than or equal to SMALLER_PRICE
        defaultBookShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllBooksByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where price is less than DEFAULT_PRICE
        defaultBookShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the bookList where price is less than UPDATED_PRICE
        defaultBookShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllBooksByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where price is greater than DEFAULT_PRICE
        defaultBookShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the bookList where price is greater than SMALLER_PRICE
        defaultBookShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllBooksByRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where rating equals to DEFAULT_RATING
        defaultBookShouldBeFound("rating.equals=" + DEFAULT_RATING);

        // Get all the bookList where rating equals to UPDATED_RATING
        defaultBookShouldNotBeFound("rating.equals=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    public void getAllBooksByRatingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where rating not equals to DEFAULT_RATING
        defaultBookShouldNotBeFound("rating.notEquals=" + DEFAULT_RATING);

        // Get all the bookList where rating not equals to UPDATED_RATING
        defaultBookShouldBeFound("rating.notEquals=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    public void getAllBooksByRatingIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where rating in DEFAULT_RATING or UPDATED_RATING
        defaultBookShouldBeFound("rating.in=" + DEFAULT_RATING + "," + UPDATED_RATING);

        // Get all the bookList where rating equals to UPDATED_RATING
        defaultBookShouldNotBeFound("rating.in=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    public void getAllBooksByRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where rating is not null
        defaultBookShouldBeFound("rating.specified=true");

        // Get all the bookList where rating is null
        defaultBookShouldNotBeFound("rating.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByRatingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where rating is greater than or equal to DEFAULT_RATING
        defaultBookShouldBeFound("rating.greaterThanOrEqual=" + DEFAULT_RATING);

        // Get all the bookList where rating is greater than or equal to UPDATED_RATING
        defaultBookShouldNotBeFound("rating.greaterThanOrEqual=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    public void getAllBooksByRatingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where rating is less than or equal to DEFAULT_RATING
        defaultBookShouldBeFound("rating.lessThanOrEqual=" + DEFAULT_RATING);

        // Get all the bookList where rating is less than or equal to SMALLER_RATING
        defaultBookShouldNotBeFound("rating.lessThanOrEqual=" + SMALLER_RATING);
    }

    @Test
    @Transactional
    public void getAllBooksByRatingIsLessThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where rating is less than DEFAULT_RATING
        defaultBookShouldNotBeFound("rating.lessThan=" + DEFAULT_RATING);

        // Get all the bookList where rating is less than UPDATED_RATING
        defaultBookShouldBeFound("rating.lessThan=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    public void getAllBooksByRatingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where rating is greater than DEFAULT_RATING
        defaultBookShouldNotBeFound("rating.greaterThan=" + DEFAULT_RATING);

        // Get all the bookList where rating is greater than SMALLER_RATING
        defaultBookShouldBeFound("rating.greaterThan=" + SMALLER_RATING);
    }


    @Test
    @Transactional
    public void getAllBooksByImageURLIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where imageURL equals to DEFAULT_IMAGE_URL
        defaultBookShouldBeFound("imageURL.equals=" + DEFAULT_IMAGE_URL);

        // Get all the bookList where imageURL equals to UPDATED_IMAGE_URL
        defaultBookShouldNotBeFound("imageURL.equals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllBooksByImageURLIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where imageURL not equals to DEFAULT_IMAGE_URL
        defaultBookShouldNotBeFound("imageURL.notEquals=" + DEFAULT_IMAGE_URL);

        // Get all the bookList where imageURL not equals to UPDATED_IMAGE_URL
        defaultBookShouldBeFound("imageURL.notEquals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllBooksByImageURLIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where imageURL in DEFAULT_IMAGE_URL or UPDATED_IMAGE_URL
        defaultBookShouldBeFound("imageURL.in=" + DEFAULT_IMAGE_URL + "," + UPDATED_IMAGE_URL);

        // Get all the bookList where imageURL equals to UPDATED_IMAGE_URL
        defaultBookShouldNotBeFound("imageURL.in=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllBooksByImageURLIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where imageURL is not null
        defaultBookShouldBeFound("imageURL.specified=true");

        // Get all the bookList where imageURL is null
        defaultBookShouldNotBeFound("imageURL.specified=false");
    }
                @Test
    @Transactional
    public void getAllBooksByImageURLContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where imageURL contains DEFAULT_IMAGE_URL
        defaultBookShouldBeFound("imageURL.contains=" + DEFAULT_IMAGE_URL);

        // Get all the bookList where imageURL contains UPDATED_IMAGE_URL
        defaultBookShouldNotBeFound("imageURL.contains=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllBooksByImageURLNotContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where imageURL does not contain DEFAULT_IMAGE_URL
        defaultBookShouldNotBeFound("imageURL.doesNotContain=" + DEFAULT_IMAGE_URL);

        // Get all the bookList where imageURL does not contain UPDATED_IMAGE_URL
        defaultBookShouldBeFound("imageURL.doesNotContain=" + UPDATED_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllBooksByLanguageIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where language equals to DEFAULT_LANGUAGE
        defaultBookShouldBeFound("language.equals=" + DEFAULT_LANGUAGE);

        // Get all the bookList where language equals to UPDATED_LANGUAGE
        defaultBookShouldNotBeFound("language.equals=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    public void getAllBooksByLanguageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where language not equals to DEFAULT_LANGUAGE
        defaultBookShouldNotBeFound("language.notEquals=" + DEFAULT_LANGUAGE);

        // Get all the bookList where language not equals to UPDATED_LANGUAGE
        defaultBookShouldBeFound("language.notEquals=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    public void getAllBooksByLanguageIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where language in DEFAULT_LANGUAGE or UPDATED_LANGUAGE
        defaultBookShouldBeFound("language.in=" + DEFAULT_LANGUAGE + "," + UPDATED_LANGUAGE);

        // Get all the bookList where language equals to UPDATED_LANGUAGE
        defaultBookShouldNotBeFound("language.in=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    public void getAllBooksByLanguageIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where language is not null
        defaultBookShouldBeFound("language.specified=true");

        // Get all the bookList where language is null
        defaultBookShouldNotBeFound("language.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByFormatIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where format equals to DEFAULT_FORMAT
        defaultBookShouldBeFound("format.equals=" + DEFAULT_FORMAT);

        // Get all the bookList where format equals to UPDATED_FORMAT
        defaultBookShouldNotBeFound("format.equals=" + UPDATED_FORMAT);
    }

    @Test
    @Transactional
    public void getAllBooksByFormatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where format not equals to DEFAULT_FORMAT
        defaultBookShouldNotBeFound("format.notEquals=" + DEFAULT_FORMAT);

        // Get all the bookList where format not equals to UPDATED_FORMAT
        defaultBookShouldBeFound("format.notEquals=" + UPDATED_FORMAT);
    }

    @Test
    @Transactional
    public void getAllBooksByFormatIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where format in DEFAULT_FORMAT or UPDATED_FORMAT
        defaultBookShouldBeFound("format.in=" + DEFAULT_FORMAT + "," + UPDATED_FORMAT);

        // Get all the bookList where format equals to UPDATED_FORMAT
        defaultBookShouldNotBeFound("format.in=" + UPDATED_FORMAT);
    }

    @Test
    @Transactional
    public void getAllBooksByFormatIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where format is not null
        defaultBookShouldBeFound("format.specified=true");

        // Get all the bookList where format is null
        defaultBookShouldNotBeFound("format.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByPaperBackQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where paperBackQuantity equals to DEFAULT_PAPER_BACK_QUANTITY
        defaultBookShouldBeFound("paperBackQuantity.equals=" + DEFAULT_PAPER_BACK_QUANTITY);

        // Get all the bookList where paperBackQuantity equals to UPDATED_PAPER_BACK_QUANTITY
        defaultBookShouldNotBeFound("paperBackQuantity.equals=" + UPDATED_PAPER_BACK_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllBooksByPaperBackQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where paperBackQuantity not equals to DEFAULT_PAPER_BACK_QUANTITY
        defaultBookShouldNotBeFound("paperBackQuantity.notEquals=" + DEFAULT_PAPER_BACK_QUANTITY);

        // Get all the bookList where paperBackQuantity not equals to UPDATED_PAPER_BACK_QUANTITY
        defaultBookShouldBeFound("paperBackQuantity.notEquals=" + UPDATED_PAPER_BACK_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllBooksByPaperBackQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where paperBackQuantity in DEFAULT_PAPER_BACK_QUANTITY or UPDATED_PAPER_BACK_QUANTITY
        defaultBookShouldBeFound("paperBackQuantity.in=" + DEFAULT_PAPER_BACK_QUANTITY + "," + UPDATED_PAPER_BACK_QUANTITY);

        // Get all the bookList where paperBackQuantity equals to UPDATED_PAPER_BACK_QUANTITY
        defaultBookShouldNotBeFound("paperBackQuantity.in=" + UPDATED_PAPER_BACK_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllBooksByPaperBackQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where paperBackQuantity is not null
        defaultBookShouldBeFound("paperBackQuantity.specified=true");

        // Get all the bookList where paperBackQuantity is null
        defaultBookShouldNotBeFound("paperBackQuantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByPaperBackQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where paperBackQuantity is greater than or equal to DEFAULT_PAPER_BACK_QUANTITY
        defaultBookShouldBeFound("paperBackQuantity.greaterThanOrEqual=" + DEFAULT_PAPER_BACK_QUANTITY);

        // Get all the bookList where paperBackQuantity is greater than or equal to UPDATED_PAPER_BACK_QUANTITY
        defaultBookShouldNotBeFound("paperBackQuantity.greaterThanOrEqual=" + UPDATED_PAPER_BACK_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllBooksByPaperBackQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where paperBackQuantity is less than or equal to DEFAULT_PAPER_BACK_QUANTITY
        defaultBookShouldBeFound("paperBackQuantity.lessThanOrEqual=" + DEFAULT_PAPER_BACK_QUANTITY);

        // Get all the bookList where paperBackQuantity is less than or equal to SMALLER_PAPER_BACK_QUANTITY
        defaultBookShouldNotBeFound("paperBackQuantity.lessThanOrEqual=" + SMALLER_PAPER_BACK_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllBooksByPaperBackQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where paperBackQuantity is less than DEFAULT_PAPER_BACK_QUANTITY
        defaultBookShouldNotBeFound("paperBackQuantity.lessThan=" + DEFAULT_PAPER_BACK_QUANTITY);

        // Get all the bookList where paperBackQuantity is less than UPDATED_PAPER_BACK_QUANTITY
        defaultBookShouldBeFound("paperBackQuantity.lessThan=" + UPDATED_PAPER_BACK_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllBooksByPaperBackQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where paperBackQuantity is greater than DEFAULT_PAPER_BACK_QUANTITY
        defaultBookShouldNotBeFound("paperBackQuantity.greaterThan=" + DEFAULT_PAPER_BACK_QUANTITY);

        // Get all the bookList where paperBackQuantity is greater than SMALLER_PAPER_BACK_QUANTITY
        defaultBookShouldBeFound("paperBackQuantity.greaterThan=" + SMALLER_PAPER_BACK_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllBooksByPublicationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publicationDate equals to DEFAULT_PUBLICATION_DATE
        defaultBookShouldBeFound("publicationDate.equals=" + DEFAULT_PUBLICATION_DATE);

        // Get all the bookList where publicationDate equals to UPDATED_PUBLICATION_DATE
        defaultBookShouldNotBeFound("publicationDate.equals=" + UPDATED_PUBLICATION_DATE);
    }

    @Test
    @Transactional
    public void getAllBooksByPublicationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publicationDate not equals to DEFAULT_PUBLICATION_DATE
        defaultBookShouldNotBeFound("publicationDate.notEquals=" + DEFAULT_PUBLICATION_DATE);

        // Get all the bookList where publicationDate not equals to UPDATED_PUBLICATION_DATE
        defaultBookShouldBeFound("publicationDate.notEquals=" + UPDATED_PUBLICATION_DATE);
    }

    @Test
    @Transactional
    public void getAllBooksByPublicationDateIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publicationDate in DEFAULT_PUBLICATION_DATE or UPDATED_PUBLICATION_DATE
        defaultBookShouldBeFound("publicationDate.in=" + DEFAULT_PUBLICATION_DATE + "," + UPDATED_PUBLICATION_DATE);

        // Get all the bookList where publicationDate equals to UPDATED_PUBLICATION_DATE
        defaultBookShouldNotBeFound("publicationDate.in=" + UPDATED_PUBLICATION_DATE);
    }

    @Test
    @Transactional
    public void getAllBooksByPublicationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publicationDate is not null
        defaultBookShouldBeFound("publicationDate.specified=true");

        // Get all the bookList where publicationDate is null
        defaultBookShouldNotBeFound("publicationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByPublicationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publicationDate is greater than or equal to DEFAULT_PUBLICATION_DATE
        defaultBookShouldBeFound("publicationDate.greaterThanOrEqual=" + DEFAULT_PUBLICATION_DATE);

        // Get all the bookList where publicationDate is greater than or equal to UPDATED_PUBLICATION_DATE
        defaultBookShouldNotBeFound("publicationDate.greaterThanOrEqual=" + UPDATED_PUBLICATION_DATE);
    }

    @Test
    @Transactional
    public void getAllBooksByPublicationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publicationDate is less than or equal to DEFAULT_PUBLICATION_DATE
        defaultBookShouldBeFound("publicationDate.lessThanOrEqual=" + DEFAULT_PUBLICATION_DATE);

        // Get all the bookList where publicationDate is less than or equal to SMALLER_PUBLICATION_DATE
        defaultBookShouldNotBeFound("publicationDate.lessThanOrEqual=" + SMALLER_PUBLICATION_DATE);
    }

    @Test
    @Transactional
    public void getAllBooksByPublicationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publicationDate is less than DEFAULT_PUBLICATION_DATE
        defaultBookShouldNotBeFound("publicationDate.lessThan=" + DEFAULT_PUBLICATION_DATE);

        // Get all the bookList where publicationDate is less than UPDATED_PUBLICATION_DATE
        defaultBookShouldBeFound("publicationDate.lessThan=" + UPDATED_PUBLICATION_DATE);
    }

    @Test
    @Transactional
    public void getAllBooksByPublicationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publicationDate is greater than DEFAULT_PUBLICATION_DATE
        defaultBookShouldNotBeFound("publicationDate.greaterThan=" + DEFAULT_PUBLICATION_DATE);

        // Get all the bookList where publicationDate is greater than SMALLER_PUBLICATION_DATE
        defaultBookShouldBeFound("publicationDate.greaterThan=" + SMALLER_PUBLICATION_DATE);
    }


    @Test
    @Transactional
    public void getAllBooksByIsbnIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where isbn equals to DEFAULT_ISBN
        defaultBookShouldBeFound("isbn.equals=" + DEFAULT_ISBN);

        // Get all the bookList where isbn equals to UPDATED_ISBN
        defaultBookShouldNotBeFound("isbn.equals=" + UPDATED_ISBN);
    }

    @Test
    @Transactional
    public void getAllBooksByIsbnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where isbn not equals to DEFAULT_ISBN
        defaultBookShouldNotBeFound("isbn.notEquals=" + DEFAULT_ISBN);

        // Get all the bookList where isbn not equals to UPDATED_ISBN
        defaultBookShouldBeFound("isbn.notEquals=" + UPDATED_ISBN);
    }

    @Test
    @Transactional
    public void getAllBooksByIsbnIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where isbn in DEFAULT_ISBN or UPDATED_ISBN
        defaultBookShouldBeFound("isbn.in=" + DEFAULT_ISBN + "," + UPDATED_ISBN);

        // Get all the bookList where isbn equals to UPDATED_ISBN
        defaultBookShouldNotBeFound("isbn.in=" + UPDATED_ISBN);
    }

    @Test
    @Transactional
    public void getAllBooksByIsbnIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where isbn is not null
        defaultBookShouldBeFound("isbn.specified=true");

        // Get all the bookList where isbn is null
        defaultBookShouldNotBeFound("isbn.specified=false");
    }
                @Test
    @Transactional
    public void getAllBooksByIsbnContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where isbn contains DEFAULT_ISBN
        defaultBookShouldBeFound("isbn.contains=" + DEFAULT_ISBN);

        // Get all the bookList where isbn contains UPDATED_ISBN
        defaultBookShouldNotBeFound("isbn.contains=" + UPDATED_ISBN);
    }

    @Test
    @Transactional
    public void getAllBooksByIsbnNotContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where isbn does not contain DEFAULT_ISBN
        defaultBookShouldNotBeFound("isbn.doesNotContain=" + DEFAULT_ISBN);

        // Get all the bookList where isbn does not contain UPDATED_ISBN
        defaultBookShouldBeFound("isbn.doesNotContain=" + UPDATED_ISBN);
    }


    @Test
    @Transactional
    public void getAllBooksByPagesIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where pages equals to DEFAULT_PAGES
        defaultBookShouldBeFound("pages.equals=" + DEFAULT_PAGES);

        // Get all the bookList where pages equals to UPDATED_PAGES
        defaultBookShouldNotBeFound("pages.equals=" + UPDATED_PAGES);
    }

    @Test
    @Transactional
    public void getAllBooksByPagesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where pages not equals to DEFAULT_PAGES
        defaultBookShouldNotBeFound("pages.notEquals=" + DEFAULT_PAGES);

        // Get all the bookList where pages not equals to UPDATED_PAGES
        defaultBookShouldBeFound("pages.notEquals=" + UPDATED_PAGES);
    }

    @Test
    @Transactional
    public void getAllBooksByPagesIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where pages in DEFAULT_PAGES or UPDATED_PAGES
        defaultBookShouldBeFound("pages.in=" + DEFAULT_PAGES + "," + UPDATED_PAGES);

        // Get all the bookList where pages equals to UPDATED_PAGES
        defaultBookShouldNotBeFound("pages.in=" + UPDATED_PAGES);
    }

    @Test
    @Transactional
    public void getAllBooksByPagesIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where pages is not null
        defaultBookShouldBeFound("pages.specified=true");

        // Get all the bookList where pages is null
        defaultBookShouldNotBeFound("pages.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByPagesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where pages is greater than or equal to DEFAULT_PAGES
        defaultBookShouldBeFound("pages.greaterThanOrEqual=" + DEFAULT_PAGES);

        // Get all the bookList where pages is greater than or equal to UPDATED_PAGES
        defaultBookShouldNotBeFound("pages.greaterThanOrEqual=" + UPDATED_PAGES);
    }

    @Test
    @Transactional
    public void getAllBooksByPagesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where pages is less than or equal to DEFAULT_PAGES
        defaultBookShouldBeFound("pages.lessThanOrEqual=" + DEFAULT_PAGES);

        // Get all the bookList where pages is less than or equal to SMALLER_PAGES
        defaultBookShouldNotBeFound("pages.lessThanOrEqual=" + SMALLER_PAGES);
    }

    @Test
    @Transactional
    public void getAllBooksByPagesIsLessThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where pages is less than DEFAULT_PAGES
        defaultBookShouldNotBeFound("pages.lessThan=" + DEFAULT_PAGES);

        // Get all the bookList where pages is less than UPDATED_PAGES
        defaultBookShouldBeFound("pages.lessThan=" + UPDATED_PAGES);
    }

    @Test
    @Transactional
    public void getAllBooksByPagesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where pages is greater than DEFAULT_PAGES
        defaultBookShouldNotBeFound("pages.greaterThan=" + DEFAULT_PAGES);

        // Get all the bookList where pages is greater than SMALLER_PAGES
        defaultBookShouldBeFound("pages.greaterThan=" + SMALLER_PAGES);
    }


    @Test
    @Transactional
    public void getAllBooksByOtherDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where otherDetails equals to DEFAULT_OTHER_DETAILS
        defaultBookShouldBeFound("otherDetails.equals=" + DEFAULT_OTHER_DETAILS);

        // Get all the bookList where otherDetails equals to UPDATED_OTHER_DETAILS
        defaultBookShouldNotBeFound("otherDetails.equals=" + UPDATED_OTHER_DETAILS);
    }

    @Test
    @Transactional
    public void getAllBooksByOtherDetailsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where otherDetails not equals to DEFAULT_OTHER_DETAILS
        defaultBookShouldNotBeFound("otherDetails.notEquals=" + DEFAULT_OTHER_DETAILS);

        // Get all the bookList where otherDetails not equals to UPDATED_OTHER_DETAILS
        defaultBookShouldBeFound("otherDetails.notEquals=" + UPDATED_OTHER_DETAILS);
    }

    @Test
    @Transactional
    public void getAllBooksByOtherDetailsIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where otherDetails in DEFAULT_OTHER_DETAILS or UPDATED_OTHER_DETAILS
        defaultBookShouldBeFound("otherDetails.in=" + DEFAULT_OTHER_DETAILS + "," + UPDATED_OTHER_DETAILS);

        // Get all the bookList where otherDetails equals to UPDATED_OTHER_DETAILS
        defaultBookShouldNotBeFound("otherDetails.in=" + UPDATED_OTHER_DETAILS);
    }

    @Test
    @Transactional
    public void getAllBooksByOtherDetailsIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where otherDetails is not null
        defaultBookShouldBeFound("otherDetails.specified=true");

        // Get all the bookList where otherDetails is null
        defaultBookShouldNotBeFound("otherDetails.specified=false");
    }
                @Test
    @Transactional
    public void getAllBooksByOtherDetailsContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where otherDetails contains DEFAULT_OTHER_DETAILS
        defaultBookShouldBeFound("otherDetails.contains=" + DEFAULT_OTHER_DETAILS);

        // Get all the bookList where otherDetails contains UPDATED_OTHER_DETAILS
        defaultBookShouldNotBeFound("otherDetails.contains=" + UPDATED_OTHER_DETAILS);
    }

    @Test
    @Transactional
    public void getAllBooksByOtherDetailsNotContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where otherDetails does not contain DEFAULT_OTHER_DETAILS
        defaultBookShouldNotBeFound("otherDetails.doesNotContain=" + DEFAULT_OTHER_DETAILS);

        // Get all the bookList where otherDetails does not contain UPDATED_OTHER_DETAILS
        defaultBookShouldBeFound("otherDetails.doesNotContain=" + UPDATED_OTHER_DETAILS);
    }


    @Test
    @Transactional
    public void getAllBooksByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);
        Category category = CategoryResourceIT.createEntity(em);
        em.persist(category);
        em.flush();
        book.setCategory(category);
        bookRepository.saveAndFlush(book);
        Long categoryId = category.getId();

        // Get all the bookList where category equals to categoryId
        defaultBookShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the bookList where category equals to categoryId + 1
        defaultBookShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }


    @Test
    @Transactional
    public void getAllBooksByAuthorIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);
        Author author = AuthorResourceIT.createEntity(em);
        em.persist(author);
        em.flush();
        book.addAuthor(author);
        bookRepository.saveAndFlush(book);
        Long authorId = author.getId();

        // Get all the bookList where author equals to authorId
        defaultBookShouldBeFound("authorId.equals=" + authorId);

        // Get all the bookList where author equals to authorId + 1
        defaultBookShouldNotBeFound("authorId.equals=" + (authorId + 1));
    }


    @Test
    @Transactional
    public void getAllBooksByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);
        Discount discount = DiscountResourceIT.createEntity(em);
        em.persist(discount);
        em.flush();
        book.setDiscount(discount);
        bookRepository.saveAndFlush(book);
        Long discountId = discount.getId();

        // Get all the bookList where discount equals to discountId
        defaultBookShouldBeFound("discountId.equals=" + discountId);

        // Get all the bookList where discount equals to discountId + 1
        defaultBookShouldNotBeFound("discountId.equals=" + (discountId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBookShouldBeFound(String filter) throws Exception {
        restBookMockMvc.perform(get("/api/books?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(book.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].imageURL").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].format").value(hasItem(DEFAULT_FORMAT.toString())))
            .andExpect(jsonPath("$.[*].paperBackQuantity").value(hasItem(DEFAULT_PAPER_BACK_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].publicationDate").value(hasItem(DEFAULT_PUBLICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].isbn").value(hasItem(DEFAULT_ISBN)))
            .andExpect(jsonPath("$.[*].pages").value(hasItem(DEFAULT_PAGES)))
            .andExpect(jsonPath("$.[*].otherDetails").value(hasItem(DEFAULT_OTHER_DETAILS)));

        // Check, that the count call also returns 1
        restBookMockMvc.perform(get("/api/books/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBookShouldNotBeFound(String filter) throws Exception {
        restBookMockMvc.perform(get("/api/books?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBookMockMvc.perform(get("/api/books/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingBook() throws Exception {
        // Get the book
        restBookMockMvc.perform(get("/api/books/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Update the book
        Book updatedBook = bookRepository.findById(book.getId()).get();
        // Disconnect from session so that the updates on updatedBook are not directly saved in db
        em.detach(updatedBook);
        updatedBook
            .title(UPDATED_TITLE)
            .price(UPDATED_PRICE)
            .rating(UPDATED_RATING)
            .imageURL(UPDATED_IMAGE_URL)
            .language(UPDATED_LANGUAGE)
            .format(UPDATED_FORMAT)
            .paperBackQuantity(UPDATED_PAPER_BACK_QUANTITY)
            .publicationDate(UPDATED_PUBLICATION_DATE)
            .isbn(UPDATED_ISBN)
            .pages(UPDATED_PAGES)
            .otherDetails(UPDATED_OTHER_DETAILS);
        BookDTO bookDTO = bookMapper.toDto(updatedBook);

        restBookMockMvc.perform(put("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isOk());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBook.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testBook.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testBook.getImageURL()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testBook.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testBook.getFormat()).isEqualTo(UPDATED_FORMAT);
        assertThat(testBook.getPaperBackQuantity()).isEqualTo(UPDATED_PAPER_BACK_QUANTITY);
        assertThat(testBook.getPublicationDate()).isEqualTo(UPDATED_PUBLICATION_DATE);
        assertThat(testBook.getIsbn()).isEqualTo(UPDATED_ISBN);
        assertThat(testBook.getPages()).isEqualTo(UPDATED_PAGES);
        assertThat(testBook.getOtherDetails()).isEqualTo(UPDATED_OTHER_DETAILS);
    }

    @Test
    @Transactional
    public void updateNonExistingBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookMockMvc.perform(put("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        int databaseSizeBeforeDelete = bookRepository.findAll().size();

        // Delete the book
        restBookMockMvc.perform(delete("/api/books/{id}", book.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
