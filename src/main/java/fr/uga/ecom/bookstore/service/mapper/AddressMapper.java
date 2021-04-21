package fr.uga.ecom.bookstore.service.mapper;


import fr.uga.ecom.bookstore.domain.*;
import fr.uga.ecom.bookstore.service.dto.AddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomUserMapper.class})
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {

    @Mapping(source = "customer.id", target = "customerId")
    AddressDTO toDto(Address address);

    @Mapping(source = "customerId", target = "customer")
    Address toEntity(AddressDTO addressDTO);

    default Address fromId(Long id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        address.setId(id);
        return address;
    }
}
