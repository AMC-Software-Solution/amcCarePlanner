package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.EmployeeService;
import com.amc.careplanner.web.rest.EmployeeResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.EmployeeDTO;
import com.amc.careplanner.service.ext.EmployeeServiceExt;
import com.amc.careplanner.service.dto.EmployeeCriteria;
import com.amc.careplanner.service.EmployeeQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.amc.careplanner.domain.Employee}.
 */
@RestController
@RequestMapping("/api/v1")
public class EmployeeResourceExt extends EmployeeResource{

    private final Logger log = LoggerFactory.getLogger(EmployeeResourceExt.class);

    private static final String ENTITY_NAME = "employee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeServiceExt employeeServiceExt;

    private final EmployeeQueryService employeeQueryService;

    public EmployeeResourceExt(EmployeeServiceExt employeeServiceExt, EmployeeQueryService employeeQueryService) {
        super(employeeServiceExt,employeeQueryService);
    	this.employeeServiceExt = employeeServiceExt;
        this.employeeQueryService = employeeQueryService;
    }

  
}