package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.SystemEventsHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SystemEventsHistory} and its DTO {@link SystemEventsHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface SystemEventsHistoryMapper extends EntityMapper<SystemEventsHistoryDTO, SystemEventsHistory> {

    @Mapping(source = "triggedBy.id", target = "triggedById")
    @Mapping(source = "triggedBy.login", target = "triggedByLogin")
    SystemEventsHistoryDTO toDto(SystemEventsHistory systemEventsHistory);

    @Mapping(source = "triggedById", target = "triggedBy")
    SystemEventsHistory toEntity(SystemEventsHistoryDTO systemEventsHistoryDTO);

    default SystemEventsHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        SystemEventsHistory systemEventsHistory = new SystemEventsHistory();
        systemEventsHistory.setId(id);
        return systemEventsHistory;
    }
}
