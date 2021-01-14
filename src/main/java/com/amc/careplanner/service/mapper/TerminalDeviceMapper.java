package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.TerminalDeviceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TerminalDevice} and its DTO {@link TerminalDeviceDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface TerminalDeviceMapper extends EntityMapper<TerminalDeviceDTO, TerminalDevice> {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.employeeCode", target = "employeeEmployeeCode")
    TerminalDeviceDTO toDto(TerminalDevice terminalDevice);

    @Mapping(source = "employeeId", target = "employee")
    TerminalDevice toEntity(TerminalDeviceDTO terminalDeviceDTO);

    default TerminalDevice fromId(Long id) {
        if (id == null) {
            return null;
        }
        TerminalDevice terminalDevice = new TerminalDevice();
        terminalDevice.setId(id);
        return terminalDevice;
    }
}
