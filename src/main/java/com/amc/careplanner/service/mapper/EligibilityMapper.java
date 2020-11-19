package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.EligibilityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Eligibility} and its DTO {@link EligibilityDTO}.
 */
@Mapper(componentModel = "spring", uses = {EligibilityTypeMapper.class, EmployeeMapper.class})
public interface EligibilityMapper extends EntityMapper<EligibilityDTO, Eligibility> {

    @Mapping(source = "eligibilityType.id", target = "eligibilityTypeId")
    @Mapping(source = "eligibilityType.eligibilityType", target = "eligibilityTypeEligibilityType")
    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.employeeCode", target = "employeeEmployeeCode")
    EligibilityDTO toDto(Eligibility eligibility);

    @Mapping(source = "eligibilityTypeId", target = "eligibilityType")
    @Mapping(source = "employeeId", target = "employee")
    Eligibility toEntity(EligibilityDTO eligibilityDTO);

    default Eligibility fromId(Long id) {
        if (id == null) {
            return null;
        }
        Eligibility eligibility = new Eligibility();
        eligibility.setId(id);
        return eligibility;
    }
}
