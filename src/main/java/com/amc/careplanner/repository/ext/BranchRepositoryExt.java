package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.Branch;
import com.amc.careplanner.repository.BranchRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Branch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BranchRepositoryExt extends BranchRepository  {
}
