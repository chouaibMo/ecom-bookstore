package fr.uga.ecom.bookstore.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import fr.uga.ecom.bookstore.domain.enumeration.Country;

/**
 * A DTO for the {@link fr.uga.ecom.bookstore.domain.Author} entity.
 */
public class AuthorDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private String description;

    private Country country;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthorDTO)) {
            return false;
        }

        return id != null && id.equals(((AuthorDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuthorDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", country='" + getCountry() + "'" +
            "}";
    }
}
