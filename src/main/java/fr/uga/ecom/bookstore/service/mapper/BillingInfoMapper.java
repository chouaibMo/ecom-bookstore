package fr.uga.ecom.bookstore.service.mapper;


import fr.uga.ecom.bookstore.domain.*;
import fr.uga.ecom.bookstore.service.dto.BillingInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BillingInfo} and its DTO {@link BillingInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomUserMapper.class})
public interface BillingInfoMapper extends EntityMapper<BillingInfoDTO, BillingInfo> {

    @Mapping(source = "customer.id", target = "customerId")
    BillingInfoDTO toDto(BillingInfo billingInfo);

    @Mapping(source = "customerId", target = "customer")
    BillingInfo toEntity(BillingInfoDTO billingInfoDTO);

    default BillingInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        BillingInfo billingInfo = new BillingInfo();
        billingInfo.setId(id);
        return billingInfo;
    }
}
