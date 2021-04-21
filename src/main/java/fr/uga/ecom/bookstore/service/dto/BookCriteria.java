package fr.uga.ecom.bookstore.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import fr.uga.ecom.bookstore.domain.enumeration.Language;
import fr.uga.ecom.bookstore.domain.enumeration.BookType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link fr.uga.ecom.bookstore.domain.Book} entity. This class is used
 * in {@link fr.uga.ecom.bookstore.web.rest.BookResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /books?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BookCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Language
     */
    public static class LanguageFilter extends Filter<Language> {

        public LanguageFilter() {
        }

        public LanguageFilter(LanguageFilter filter) {
            super(filter);
        }

        @Override
        public LanguageFilter copy() {
            return new LanguageFilter(this);
        }

    }
    /**
     * Class for filtering BookType
     */
    public static class BookTypeFilter extends Filter<BookType> {

        public BookTypeFilter() {
        }

        public BookTypeFilter(BookTypeFilter filter) {
            super(filter);
        }

        @Override
        public BookTypeFilter copy() {
            return new BookTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private FloatFilter price;

    private FloatFilter rating;

    private StringFilter imageURL;

    private LanguageFilter language;

    private BookTypeFilter format;

    private LongFilter paperBackQuantity;

    private LocalDateFilter publicationDate;

    private StringFilter isbn;

    private IntegerFilter pages;

    private StringFilter otherDetails;

    private LongFilter categoryId;

    private LongFilter authorId;

    private LongFilter discountId;

    public BookCriteria() {
    }

    public BookCriteria(BookCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.rating = other.rating == null ? null : other.rating.copy();
        this.imageURL = other.imageURL == null ? null : other.imageURL.copy();
        this.language = other.language == null ? null : other.language.copy();
        this.format = other.format == null ? null : other.format.copy();
        this.paperBackQuantity = other.paperBackQuantity == null ? null : other.paperBackQuantity.copy();
        this.publicationDate = other.publicationDate == null ? null : other.publicationDate.copy();
        this.isbn = other.isbn == null ? null : other.isbn.copy();
        this.pages = other.pages == null ? null : other.pages.copy();
        this.otherDetails = other.otherDetails == null ? null : other.otherDetails.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
        this.authorId = other.authorId == null ? null : other.authorId.copy();
        this.discountId = other.discountId == null ? null : other.discountId.copy();
    }

    @Override
    public BookCriteria copy() {
        return new BookCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public FloatFilter getPrice() {
        return price;
    }

    public void setPrice(FloatFilter price) {
        this.price = price;
    }

    public FloatFilter getRating() {
        return rating;
    }

    public void setRating(FloatFilter rating) {
        this.rating = rating;
    }

    public StringFilter getImageURL() {
        return imageURL;
    }

    public void setImageURL(StringFilter imageURL) {
        this.imageURL = imageURL;
    }

    public LanguageFilter getLanguage() {
        return language;
    }

    public void setLanguage(LanguageFilter language) {
        this.language = language;
    }

    public BookTypeFilter getFormat() {
        return format;
    }

    public void setFormat(BookTypeFilter format) {
        this.format = format;
    }

    public LongFilter getPaperBackQuantity() {
        return paperBackQuantity;
    }

    public void setPaperBackQuantity(LongFilter paperBackQuantity) {
        this.paperBackQuantity = paperBackQuantity;
    }

    public LocalDateFilter getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateFilter publicationDate) {
        this.publicationDate = publicationDate;
    }

    public StringFilter getIsbn() {
        return isbn;
    }

    public void setIsbn(StringFilter isbn) {
        this.isbn = isbn;
    }

    public IntegerFilter getPages() {
        return pages;
    }

    public void setPages(IntegerFilter pages) {
        this.pages = pages;
    }

    public StringFilter getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(StringFilter otherDetails) {
        this.otherDetails = otherDetails;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }

    public LongFilter getAuthorId() {
        return authorId;
    }

    public void setAuthorId(LongFilter authorId) {
        this.authorId = authorId;
    }

    public LongFilter getDiscountId() {
        return discountId;
    }

    public void setDiscountId(LongFilter discountId) {
        this.discountId = discountId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BookCriteria that = (BookCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(price, that.price) &&
            Objects.equals(rating, that.rating) &&
            Objects.equals(imageURL, that.imageURL) &&
            Objects.equals(language, that.language) &&
            Objects.equals(format, that.format) &&
            Objects.equals(paperBackQuantity, that.paperBackQuantity) &&
            Objects.equals(publicationDate, that.publicationDate) &&
            Objects.equals(isbn, that.isbn) &&
            Objects.equals(pages, that.pages) &&
            Objects.equals(otherDetails, that.otherDetails) &&
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(authorId, that.authorId) &&
            Objects.equals(discountId, that.discountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        price,
        rating,
        imageURL,
        language,
        format,
        paperBackQuantity,
        publicationDate,
        isbn,
        pages,
        otherDetails,
        categoryId,
        authorId,
        discountId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (rating != null ? "rating=" + rating + ", " : "") +
                (imageURL != null ? "imageURL=" + imageURL + ", " : "") +
                (language != null ? "language=" + language + ", " : "") +
                (format != null ? "format=" + format + ", " : "") +
                (paperBackQuantity != null ? "paperBackQuantity=" + paperBackQuantity + ", " : "") +
                (publicationDate != null ? "publicationDate=" + publicationDate + ", " : "") +
                (isbn != null ? "isbn=" + isbn + ", " : "") +
                (pages != null ? "pages=" + pages + ", " : "") +
                (otherDetails != null ? "otherDetails=" + otherDetails + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
                (authorId != null ? "authorId=" + authorId + ", " : "") +
                (discountId != null ? "discountId=" + discountId + ", " : "") +
            "}";
    }

}
