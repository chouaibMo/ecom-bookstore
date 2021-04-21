package fr.uga.ecom.bookstore.service.mapper;


import fr.uga.ecom.bookstore.domain.*;
import fr.uga.ecom.bookstore.service.dto.CustomUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomUser} and its DTO {@link CustomUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, OrderMapper.class})
public interface CustomUserMapper extends EntityMapper<CustomUserDTO, CustomUser> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "cart.id", target = "cartId")
    CustomUserDTO toDto(CustomUser customUser);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "cartId", target = "cart")
    @Mapping(target = "billingInfos", ignore = true)
    @Mapping(target = "removeBillingInfo", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "removeAddress", ignore = true)
    CustomUser toEntity(CustomUserDTO customUserDTO);

    default CustomUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomUser customUser = new CustomUser();
        customUser.setId(id);
        return customUser;
    }
}
