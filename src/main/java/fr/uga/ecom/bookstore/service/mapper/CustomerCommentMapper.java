package fr.uga.ecom.bookstore.service.mapper;


import fr.uga.ecom.bookstore.domain.*;
import fr.uga.ecom.bookstore.service.dto.CustomerCommentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomerComment} and its DTO {@link CustomerCommentDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomUserMapper.class, BookMapper.class})
public interface CustomerCommentMapper extends EntityMapper<CustomerCommentDTO, CustomerComment> {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "book.id", target = "bookId")
    CustomerCommentDTO toDto(CustomerComment customerComment);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "bookId", target = "book")
    CustomerComment toEntity(CustomerCommentDTO customerCommentDTO);

    default CustomerComment fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerComment customerComment = new CustomerComment();
        customerComment.setId(id);
        return customerComment;
    }
}
