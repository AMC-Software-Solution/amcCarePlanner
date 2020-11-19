package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.BranchDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Branch} and its DTO {@link BranchDTO}.
 */
@Mapper(componentModel = "spring", uses = {TenantMapper.class})
public interface BranchMapper extends EntityMapper<BranchDTO, Branch> {

    @Mapping(source = "tenant.id", target = "tenantId")
    @Mapping(source = "tenant.tenantName", target = "tenantTenantName")
    BranchDTO toDto(Branch branch);

    @Mapping(source = "tenantId", target = "tenant")
    Branch toEntity(BranchDTO branchDTO);

    default Branch fromId(Long id) {
        if (id == null) {
            return null;
        }
        Branch branch = new Branch();
        branch.setId(id);
        return branch;
    }
}
