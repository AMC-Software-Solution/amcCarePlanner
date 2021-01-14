package com.amc.careplanner.service.ext;

import com.amc.careplanner.domain.Payroll;
import com.amc.careplanner.repository.PayrollRepository;
import com.amc.careplanner.repository.ext.PayrollRepositoryExt;
import com.amc.careplanner.service.PayrollService;
import com.amc.careplanner.service.dto.PayrollDTO;
import com.amc.careplanner.service.mapper.PayrollMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Payroll}.
 */
@Service
@Transactional
public class PayrollServiceExt extends PayrollService{

    private final Logger log = LoggerFactory.getLogger(PayrollServiceExt.class);

    private final PayrollRepositoryExt payrollRepositoryExt;

    private final PayrollMapper payrollMapper;

    public PayrollServiceExt(PayrollRepositoryExt payrollRepositoryExt, PayrollMapper payrollMapper) {
        super(payrollRepositoryExt,payrollMapper);
    	this.payrollRepositoryExt = payrollRepositoryExt;
        this.payrollMapper = payrollMapper;
    }

    /**
     * Save a payroll.
     *
     * @param payrollDTO the entity to save.
     * @return the persisted entity.
     */
    public PayrollDTO save(PayrollDTO payrollDTO) {
        log.debug("Request to save Payroll : {}", payrollDTO);
        Payroll payroll = payrollMapper.toEntity(payrollDTO);
        payroll = payrollRepositoryExt.save(payroll);
        return payrollMapper.toDto(payroll);
    }

    /**
     * Get all the payrolls.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PayrollDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Payrolls");
        return payrollRepositoryExt.findAll(pageable)
            .map(payrollMapper::toDto);
    }


    /**
     * Get one payroll by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PayrollDTO> findOne(Long id) {
        log.debug("Request to get Payroll : {}", id);
        return payrollRepositoryExt.findById(id)
            .map(payrollMapper::toDto);
    }

    /**
     * Delete the payroll by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Payroll : {}", id);
        payrollRepositoryExt.deleteById(id);
    }
}
