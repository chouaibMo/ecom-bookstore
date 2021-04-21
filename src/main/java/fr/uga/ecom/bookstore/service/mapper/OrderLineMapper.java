package fr.uga.ecom.bookstore.service.mapper;


import fr.uga.ecom.bookstore.domain.*;
import fr.uga.ecom.bookstore.service.dto.OrderLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderLine} and its DTO {@link OrderLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {BookMapper.class, OrderMapper.class})
public interface OrderLineMapper extends EntityMapper<OrderLineDTO, OrderLine> {

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "order.id", target = "orderId")
    OrderLineDTO toDto(OrderLine orderLine);

    @Mapping(source = "bookId", target = "book")
    @Mapping(source = "orderId", target = "order")
    OrderLine toEntity(OrderLineDTO orderLineDTO);

    default OrderLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderLine orderLine = new OrderLine();
        orderLine.setId(id);
        return orderLine;
    }
}
