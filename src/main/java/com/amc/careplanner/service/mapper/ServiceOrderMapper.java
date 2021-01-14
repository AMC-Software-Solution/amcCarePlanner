package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.ServiceOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceOrder} and its DTO {@link ServiceOrderDTO}.
 */
@Mapper(componentModel = "spring", uses = {CurrencyMapper.class})
public interface ServiceOrderMapper extends EntityMapper<ServiceOrderDTO, ServiceOrder> {

    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "currency.currency", target = "currencyCurrency")
    ServiceOrderDTO toDto(ServiceOrder serviceOrder);

    @Mapping(source = "currencyId", target = "currency")
    ServiceOrder toEntity(ServiceOrderDTO serviceOrderDTO);

    default ServiceOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setId(id);
        return serviceOrder;
    }
}
