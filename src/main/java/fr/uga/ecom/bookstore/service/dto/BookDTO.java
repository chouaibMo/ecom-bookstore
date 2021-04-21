package fr.uga.ecom.bookstore.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import fr.uga.ecom.bookstore.domain.enumeration.Language;
import fr.uga.ecom.bookstore.domain.enumeration.BookType;

/**
 * A DTO for the {@link fr.uga.ecom.bookstore.domain.Book} entity.
 */
public class BookDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private Float price;

    private Float rating;

    private String imageURL;

    private Language language;

    @NotNull
    private BookType format;

    private Long paperBackQuantity;

    @NotNull
    private LocalDate publicationDate;

    private String isbn;

    private Integer pages;

    private String otherDetails;


    private Long categoryId;
    private Set<AuthorDTO> authors = new HashSet<>();

    private Long discountId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public BookType getFormat() {
        return format;
    }

    public void setFormat(BookType format) {
        this.format = format;
    }

    public Long getPaperBackQuantity() {
        return paperBackQuantity;
    }

    public void setPaperBackQuantity(Long paperBackQuantity) {
        this.paperBackQuantity = paperBackQuantity;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Set<AuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorDTO> authors) {
        this.authors = authors;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookDTO)) {
            return false;
        }

        return id != null && id.equals(((BookDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookDTO{" +
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
            ", categoryId=" + getCategoryId() +
            ", authors='" + getAuthors() + "'" +
            ", discountId=" + getDiscountId() +
            "}";
    }
}
