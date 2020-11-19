package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.EmployeeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, CountryMapper.class})
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "nationality.id", target = "nationalityId")
    @Mapping(source = "nationality.countryName", target = "nationalityCountryName")
    EmployeeDTO toDto(Employee employee);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "nationalityId", target = "nationality")
    Employee toEntity(EmployeeDTO employeeDTO);

    default Employee fromId(Long id) {
        if (id == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }
}
