package com.amc.careplanner.service.ext;

import com.amc.careplanner.domain.Access;
import com.amc.careplanner.repository.AccessRepository;
import com.amc.careplanner.repository.ext.AccessRepositoryExt;
import com.amc.careplanner.service.AccessService;
import com.amc.careplanner.service.dto.AccessDTO;
import com.amc.careplanner.service.mapper.AccessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Access}.
 */
@Service
@Transactional
public class AccessServiceExt extends AccessService {

    private final Logger log = LoggerFactory.getLogger(AccessServiceExt.class);

    private final AccessRepositoryExt accessRepositoryExt;

    private final AccessMapper accessMapper;

    public AccessServiceExt(AccessRepositoryExt accessRepositoryExt, AccessMapper accessMapper) {
    	super(accessRepositoryExt,accessMapper);
        this.accessRepositoryExt = accessRepositoryExt;
        this.accessMapper = accessMapper;
    }


}
