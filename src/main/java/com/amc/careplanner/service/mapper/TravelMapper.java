package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.TravelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Travel} and its DTO {@link TravelDTO}.
 */
@Mapper(componentModel = "spring", uses = {TaskMapper.class})
public interface TravelMapper extends EntityMapper<TravelDTO, Travel> {

    @Mapping(source = "task.id", target = "taskId")
    @Mapping(source = "task.taskName", target = "taskTaskName")
    TravelDTO toDto(Travel travel);

    @Mapping(source = "taskId", target = "task")
    Travel toEntity(TravelDTO travelDTO);

    default Travel fromId(Long id) {
        if (id == null) {
            return null;
        }
        Travel travel = new Travel();
        travel.setId(id);
        return travel;
    }
}
