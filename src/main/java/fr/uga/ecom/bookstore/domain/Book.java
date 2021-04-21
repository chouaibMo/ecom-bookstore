package fr.uga.ecom.bookstore.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import fr.uga.ecom.bookstore.domain.enumeration.Language;

import fr.uga.ecom.bookstore.domain.enumeration.BookType;

/**
 * A Book.
 */
@Entity
@Table(name = "book")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "image_url")
    private String imageURL;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "format", nullable = false)
    private BookType format;

    @Column(name = "paper_back_quantity")
    private Long paperBackQuantity;

    @NotNull
    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "pages")
    private Integer pages;

    @Column(name = "other_details")
    private String otherDetails;

    @ManyToOne
    @JsonIgnoreProperties(value = "books", allowSetters = true)
    private Category category;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "book_author",
               joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"))
    private Set<Author> authors = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "books", allowSetters = true)
    private Discount discount;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Book title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getPrice() {
        return price;
    }

    public Book price(Float price) {
        this.price = price;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getRating() {
        return rating;
    }

    public Book rating(Float rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Book imageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Language getLanguage() {
        return language;
    }

    public Book language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public BookType getFormat() {
        return format;
    }

    public Book format(BookType format) {
        this.format = format;
        return this;
    }

    public void setFormat(BookType format) {
        this.format = format;
    }

    public Long getPaperBackQuantity() {
        return paperBackQuantity;
    }

    public Book paperBackQuantity(Long paperBackQuantity) {
        this.paperBackQuantity = paperBackQuantity;
        return this;
    }

    public void setPaperBackQuantity(Long paperBackQuantity) {
        this.paperBackQuantity = paperBackQuantity;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public Book publicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
        return this;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public Book isbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPages() {
        return pages;
    }

    public Book pages(Integer pages) {
        this.pages = pages;
        return this;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public Book otherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
        return this;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }

    public Category getCategory() {
        return category;
    }

    public Book category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public Book authors(Set<Author> authors) {
        this.authors = authors;
        return this;
    }

    public Book addAuthor(Author author) {
        this.authors.add(author);
        author.getBooks().add(this);
        return this;
    }

    public Book removeAuthor(Author author) {
        this.authors.remove(author);
        author.getBooks().remove(this);
        return this;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Discount getDiscount() {
        return discount;
    }

    public Book discount(Discount discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        return id != null && id.equals(((Book) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Book{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", price=" + getPrice() +
            ", rating=" + getRating() +
            ", imageURL='" + getImageURL() + "'" +
            ", language='" + getLanguage() + "'" +
            ", format='" + getFormat() + "'" +
            ", paperBackQuantity=" + getPaperBackQuantity() +
            ", publicationDate='" + getPublicationDate() + "'" +
            ", isbn='" + getIsbn() + "'" +
            ", pages=" + getPages() +
            ", otherDetails='" + getOtherDetails() + "'" +
            "}";
    }
}
