package fr.uga.ecom.bookstore.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A CustomerComment.
 */
@Entity
@Table(name = "customer_comment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "comment")
    private String comment;

    @NotNull
    @Column(name = "comment_date", nullable = false)
    private ZonedDateTime commentDate;

    @NotNull
    @Column(name = "rating", nullable = false)
    private Float rating;

    @ManyToOne
    @JsonIgnoreProperties(value = "customerComments", allowSetters = true)
    private CustomUser customer;

    @ManyToOne
    @JsonIgnoreProperties(value = "customerComments", allowSetters = true)
    private Book book;

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

    public CustomerComment title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public CustomerComment comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ZonedDateTime getCommentDate() {
        return commentDate;
    }

    public CustomerComment commentDate(ZonedDateTime commentDate) {
        this.commentDate = commentDate;
        return this;
    }

    public void setCommentDate(ZonedDateTime commentDate) {
        this.commentDate = commentDate;
    }

    public Float getRating() {
        return rating;
    }

    public CustomerComment rating(Float rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public CustomUser getCustomer() {
        return customer;
    }

    public CustomerComment customer(CustomUser customUser) {
        this.customer = customUser;
        return this;
    }

    public void setCustomer(CustomUser customUser) {
        this.customer = customUser;
    }

    public Book getBook() {
        return book;
    }

    public CustomerComment book(Book book) {
        this.book = book;
        return this;
    }

    public void setBook(Book book) {
        this.book = book;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerComment)) {
            return false;
        }
        return id != null && id.equals(((CustomerComment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerComment{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", comment='" + getComment() + "'" +
            ", commentDate='" + getCommentDate() + "'" +
            ", rating=" + getRating() +
            "}";
    }
}
