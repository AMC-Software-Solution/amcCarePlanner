package com.amc.careplanner.service.ext;

import com.amc.careplanner.domain.Branch;
import com.amc.careplanner.repository.BranchRepository;
import com.amc.careplanner.repository.ext.BranchRepositoryExt;
import com.amc.careplanner.service.BranchService;
import com.amc.careplanner.service.dto.BranchDTO;
import com.amc.careplanner.service.mapper.BranchMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Branch}.
 */
@Service
@Transactional
public class BranchServiceExt extends BranchService {

    private final Logger log = LoggerFactory.getLogger(BranchServiceExt.class);

    private final BranchRepositoryExt branchRepositoryExt;

    private final BranchMapper branchMapper;

    public BranchServiceExt(BranchRepositoryExt branchRepositoryExt, BranchMapper branchMapper) {
    	super(branchRepositoryExt,branchMapper);
        this.branchRepositoryExt = branchRepositoryExt;
        this.branchMapper = branchMapper;
    }

    /**
     * Save a branch.
     *
     * @param branchDTO the entity to save.
     * @return the persisted entity.
     */
    public BranchDTO save(BranchDTO branchDTO) {
        log.debug("Request to save Branch : {}", branchDTO);
        Branch branch = branchMapper.toEntity(branchDTO);
        branch = branchRepositoryExt.save(branch);
        return branchMapper.toDto(branch);
    }

    /**
     * Get all the branches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BranchDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Branches");
        return branchRepositoryExt.findAll(pageable)
            .map(branchMapper::toDto);
    }


    /**
     * Get one branch by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BranchDTO> findOne(Long id) {
        log.debug("Request to get Branch : {}", id);
        return branchRepositoryExt.findById(id)
            .map(branchMapper::toDto);
    }

    /**
     * Delete the branch by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Branch : {}", id);
        branchRepositoryExt.deleteById(id);
    }
}
