package fr.uga.ecom.bookstore.service.mapper;


import fr.uga.ecom.bookstore.domain.*;
import fr.uga.ecom.bookstore.service.dto.BookDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Book} and its DTO {@link BookDTO}.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class, AuthorMapper.class, DiscountMapper.class})
public interface BookMapper extends EntityMapper<BookDTO, Book> {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "discount.id", target = "discountId")
    BookDTO toDto(Book book);

    @Mapping(source = "categoryId", target = "category")
    @Mapping(target = "removeAuthor", ignore = true)
    @Mapping(source = "discountId", target = "discount")
    Book toEntity(BookDTO bookDTO);

    default Book fromId(Long id) {
        if (id == null) {
            return null;
        }
        Book book = new Book();
        book.setId(id);
        return book;
    }
}
