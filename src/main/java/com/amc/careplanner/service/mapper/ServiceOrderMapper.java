package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.ServiceOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceOrder} and its DTO {@link ServiceOrderDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServiceOrderMapper extends EntityMapper<ServiceOrderDTO, ServiceOrder> {



    default ServiceOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setId(id);
        return serviceOrder;
    }
}
