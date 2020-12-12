package com.amc.careplanner.service.ext;

import com.amc.careplanner.domain.ServiceOrder;
import com.amc.careplanner.repository.ServiceOrderRepository;
import com.amc.careplanner.repository.ext.ServiceOrderRepositoryExt;
import com.amc.careplanner.service.ServiceOrderService;
import com.amc.careplanner.service.dto.ServiceOrderDTO;
import com.amc.careplanner.service.mapper.ServiceOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceOrder}.
 */
@Service
@Transactional
public class ServiceOrderServiceExt extends ServiceOrderService{

    private final Logger log = LoggerFactory.getLogger(ServiceOrderServiceExt.class);

    private final ServiceOrderRepositoryExt serviceOrderRepositoryExt;

    private final ServiceOrderMapper serviceOrderMapper;

    public ServiceOrderServiceExt(ServiceOrderRepositoryExt serviceOrderRepositoryExt, ServiceOrderMapper serviceOrderMapper) {
        super(serviceOrderRepositoryExt,serviceOrderMapper);
    	this.serviceOrderRepositoryExt = serviceOrderRepositoryExt;
        this.serviceOrderMapper = serviceOrderMapper;
    }

    /**
     * Save a serviceOrder.
     *
     * @param serviceOrderDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceOrderDTO save(ServiceOrderDTO serviceOrderDTO) {
        log.debug("Request to save ServiceOrder : {}", serviceOrderDTO);
        ServiceOrder serviceOrder = serviceOrderMapper.toEntity(serviceOrderDTO);
        serviceOrder = serviceOrderRepositoryExt.save(serviceOrder);
        return serviceOrderMapper.toDto(serviceOrder);
    }

    /**
     * Get all the serviceOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceOrders");
        return serviceOrderRepositoryExt.findAll(pageable)
            .map(serviceOrderMapper::toDto);
    }


    /**
     * Get one serviceOrder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceOrderDTO> findOne(Long id) {
        log.debug("Request to get ServiceOrder : {}", id);
        return serviceOrderRepositoryExt.findById(id)
            .map(serviceOrderMapper::toDto);
    }

    /**
     * Delete the serviceOrder by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceOrder : {}", id);
        serviceOrderRepositoryExt.deleteById(id);
    }
}
