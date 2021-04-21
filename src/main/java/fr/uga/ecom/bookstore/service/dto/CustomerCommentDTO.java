package fr.uga.ecom.bookstore.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link fr.uga.ecom.bookstore.domain.CustomerComment} entity.
 */
public class CustomerCommentDTO implements Serializable {
    
    private Long id;

    private String title;

    private String comment;

    @NotNull
    private ZonedDateTime commentDate;

    @NotNull
    private Float rating;


    private Long customerId;

    private Long bookId;
    
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ZonedDateTime getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(ZonedDateTime commentDate) {
        this.commentDate = commentDate;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customUserId) {
        this.customerId = customUserId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerCommentDTO)) {
            return false;
        }

        return id != null && id.equals(((CustomerCommentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerCommentDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", comment='" + getComment() + "'" +
            ", commentDate='" + getCommentDate() + "'" +
            ", rating=" + getRating() +
            ", customerId=" + getCustomerId() +
            ", bookId=" + getBookId() +
            "}";
    }
}
