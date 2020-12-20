package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.EmployeeService;
import com.amc.careplanner.service.MailService;
import com.amc.careplanner.web.rest.EmployeeResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.web.rest.vm.ManagedUserVM;
import com.amc.careplanner.service.dto.EmployeeDTO;
import com.amc.careplanner.service.ext.EmployeeServiceExt;
import com.amc.careplanner.service.ext.UserServiceExt;
import com.amc.careplanner.service.mapper.UserMapper;
import com.amc.careplanner.service.dto.EmployeeCriteria;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.domain.enumeration.Gender;
import com.amc.careplanner.repository.ext.UserRepositoryExt;
import com.amc.careplanner.security.AuthoritiesConstants;
import com.amc.careplanner.security.SecurityUtils;
import com.amc.careplanner.service.EmailAlreadyUsedException;
import com.amc.careplanner.service.EmployeeQueryService;

import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.ZonedDateTime;
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
    

	private final UserRepositoryExt userRepositoryExt;

	private final UserServiceExt userServiceExt;

	private final UserMapper userMapper;
	
	private final MailService mailService;
	


    public EmployeeResourceExt(EmployeeServiceExt employeeServiceExt, EmployeeQueryService employeeQueryService,
    	    UserRepositoryExt userRepositoryExt, UserServiceExt userServiceExt, UserMapper userMapper, MailService mailService) {
        super(employeeServiceExt,employeeQueryService);
    	this.employeeServiceExt = employeeServiceExt;
        this.employeeQueryService = employeeQueryService;
        this.userRepositoryExt = userRepositoryExt;
        this.userServiceExt = userServiceExt;
        this.userMapper = userMapper;
        this.mailService = mailService;
   
    }
    


    @PostMapping("/create_employee_login")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.COMPANY_ADMIN + "\")")
    public ResponseEntity<EmployeeDTO> createEmployeeWithLogin(@Valid @RequestBody EmployeeDTO employeeDTO) throws URISyntaxException {
        log.debug("REST request to save Employee : {}", employeeDTO);
        if (employeeDTO.getId() != null) {
            throw new BadRequestAlertException("A new employee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        // the idea is to generate an employee code, which will allow us to 
        // uniquely identify an employee by their FirstName Initial and Last Name
		employeeDTO.setEmployeeCode(employeeDTO.getFirstName().toUpperCase() + "_" + employeeDTO.getMiddleInitial() + "_" + employeeDTO.getLastName());

		// We have decided to use and store the user.login as clientID
		String loggedInAdminUserEmail = SecurityUtils.getCurrentUserLogin().get();
		User loggedInAdminUser = userRepositoryExt.findOneByEmailIgnoreCase(loggedInAdminUserEmail).get();
		if (StringUtils.isEmpty(loggedInAdminUser.getLogin())) {
			throw new BadRequestAlertException("Employee Creation error, registered has no client", ENTITY_NAME,
					"employeewithnoclient");
		}
		
		ManagedUserVM managedUserVM = new ManagedUserVM();

		// this should be a system config settings weather to allow email activation
		// link for 2 factor Authentication or just allow user to use any email with out
		// double checking it.
		managedUserVM.setActivated(true);
		if (StringUtils.isEmpty(employeeDTO.getEmail())) {
			String email = employeeDTO.getEmployeeCode().toLowerCase() + "@amacareplanner.com";
			managedUserVM.setEmail(email);
			employeeDTO.setEmail(email);
		} else {
			managedUserVM.setEmail(employeeDTO.getEmail());
		}
		
		// default profile icons
        String imageUrl = null;
    	if (StringUtils.isEmpty(employeeDTO.getPhotoUrl())) {
			if ( employeeDTO.getGender() == Gender.MALE) {
				imageUrl = "https://www.clipartkey.com/mpngs/m/282-2825385_profile-picture-placeholder-blue.png";
			} else {
				imageUrl = "https://www.rsa.com.np/wp-content/uploads/2016/02/icon-user-default.png";	
			}
			employeeDTO.setPhotoUrl(imageUrl);
		}
    	
		managedUserVM.setFirstName(employeeDTO.getFirstName());
		managedUserVM.setLastName(employeeDTO.getLastName());
		managedUserVM.setLangKey("en");
		managedUserVM.setPassword("onetimepassword");
		managedUserVM.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
		managedUserVM.setCreatedDate(ZonedDateTime.now().toInstant());
		managedUserVM.setLogin(loggedInAdminUser.getLogin());
		managedUserVM.setImageUrl(imageUrl);
		
		

		userServiceExt.registerUser(managedUserVM, managedUserVM.getPassword());
		User user = userRepositoryExt.findOneByEmailIgnoreCase(employeeDTO.getEmail()).get();
		if (user == null) {
			throw new BadRequestAlertException("Employee Creation error, user could not be created", ENTITY_NAME,
					"usercouldnotbecreated");

		}
		

		
		employeeDTO.setUserId(user.getId());
		employeeDTO.setClientId(Long.valueOf(loggedInAdminUser.getLogin()));
		employeeDTO.setAcruedHolidayHours(0);
		employeeDTO.setLastUpdatedDate(ZonedDateTime.now());
		employeeDTO.setClientId(Long.valueOf(loggedInAdminUser.getLogin()));
		// generate Random number 
		//employeeDTO.setPinCode(pinCode);
        EmployeeDTO result = employeeServiceExt.save(employeeDTO);
      
        
        
        if (result == null ) {
        	throw new BadRequestAlertException("Employee Creation error, user could not be created", ENTITY_NAME,
					"usercouldnotbecreated");
        }
        mailService.sendCreationEmail(user);
        return ResponseEntity.created(new URI("/api/employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    @GetMapping("/get_employees_by_client_id")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(EmployeeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Employees by criteria: {}", criteria);
        EmployeeCriteria employeeCriteria = new EmployeeCriteria();
		LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(getClientIdFromLoggedInUser());
		employeeCriteria.setClientId(longFilterForClientId);
        Page<EmployeeDTO> page = employeeQueryService.findByCriteria(employeeCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    
    @PutMapping("/update_employee_by_client_id")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.COMPANY_ADMIN + "\")")
    public ResponseEntity<EmployeeDTO> updateEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) throws URISyntaxException {
        log.debug("REST request to update Employee : {}", employeeDTO);
        if (employeeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        User user = userRepositoryExt.findOneWithAuthoritiesByEmailIgnoreCase(employeeDTO.getEmail()).get();
        
        if (employeeDTO != null && employeeDTO.getClientId() != null && employeeDTO.getClientId() != getClientIdFromLoggedInUser()) {
        	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
        }
        
        String imageUrl = null;
    	if (StringUtils.isEmpty(employeeDTO.getPhotoUrl())) {
			if ( employeeDTO.getGender() == Gender.MALE) {
				imageUrl = "https://www.clipartkey.com/mpngs/m/282-2825385_profile-picture-placeholder-blue.png";
			} else {
				imageUrl = "https://www.rsa.com.np/wp-content/uploads/2016/02/icon-user-default.png";	
			}
			employeeDTO.setPhotoUrl(imageUrl);
		}
        
    	
		if (user != null) {
			// update all fields
			user.setFirstName(employeeDTO.getFirstName());
			//user.setLogin(employeeDTO.getLogin());
			user.setEmail(employeeDTO.getEmail());
			user.setLastName(employeeDTO.getLastName());
			//user.setActivated(employeeDTO.getActive());
			user.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().get());
			user.setLastModifiedDate(Instant.now());
			user.setImageUrl(imageUrl);
			userServiceExt.updateUser(userMapper.userToUserDTO(user));
			
		}
        
        EmployeeDTO result = employeeServiceExt.save(employeeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeDTO.getId().toString()))
            .body(result);
    }
    
    
    @GetMapping("/get_employee_by_client_id/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id) {
        log.debug("REST request to get Employee : {}", id);
        Optional<EmployeeDTO> employeeDTO = employeeServiceExt.findOne(id);
        if (employeeDTO.get() != null && employeeDTO.get().getClientId() != null && employeeDTO.get().getClientId() != getClientIdFromLoggedInUser()) {
        	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
        }
        return ResponseUtil.wrapOrNotFound(employeeDTO);
    }
    
 
    
    
    private Long getClientIdFromLoggedInUser() {
    	Long clientId = 0L;
    	String loggedInAdminUserEmail = SecurityUtils.getCurrentUserLogin().get();
		User loggedInAdminUser = userRepositoryExt.findOneByEmailIgnoreCase(loggedInAdminUserEmail).get();
		
		if(loggedInAdminUser != null) {
			clientId = Long.valueOf(loggedInAdminUser.getLogin());
		}
		
		return clientId;
    }
  
}
