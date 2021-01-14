package com.amc.careplanner.service.ext;

import com.amc.careplanner.domain.Employee;
import com.amc.careplanner.repository.ext.EmployeeRepositoryExt;
import com.amc.careplanner.service.EmployeeService;
import com.amc.careplanner.service.dto.EmployeeDTO;
import com.amc.careplanner.service.mapper.EmployeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Employee}.
 */



@Service
@Transactional
public class EmployeeServiceExt extends EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeServiceExt.class);

    public final EmployeeRepositoryExt employeeRepositoryExt;

    private final EmployeeMapper employeeMapper;

    public EmployeeServiceExt(EmployeeRepositoryExt employeeRepositoryExt, EmployeeMapper employeeMapper) {
    	super(employeeRepositoryExt,employeeMapper);
        this.employeeMapper = employeeMapper;
        this.employeeRepositoryExt =employeeRepositoryExt;
    }

   
}
