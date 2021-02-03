package com.amc.careplanner.web.rest.ext;

import com.amc.careplanner.service.NotificationService;
import com.amc.careplanner.web.rest.NotificationResource;
import com.amc.careplanner.web.rest.errors.BadRequestAlertException;
import com.amc.careplanner.service.dto.NotificationDTO;
import com.amc.careplanner.service.dto.TaskCriteria;
import com.amc.careplanner.service.dto.TaskDTO;
import com.amc.careplanner.service.ext.NotificationServiceExt;
import com.amc.careplanner.service.ext.SystemEventsHistoryServiceExt;
import com.amc.careplanner.utils.CommonUtils;
import com.amc.careplanner.utils.Constants;
import com.amc.careplanner.utils.RandomUtil;
import com.amc.careplanner.service.dto.ClientDTO;
import com.amc.careplanner.service.dto.CurrencyDTO;
import com.amc.careplanner.service.dto.NotificationCriteria;
import com.amc.careplanner.domain.User;
import com.amc.careplanner.repository.ext.UserRepositoryExt;
import com.amc.careplanner.s3.S3Service;
import com.amc.careplanner.security.AuthoritiesConstants;
import com.amc.careplanner.security.SecurityUtils;
import com.amc.careplanner.service.NotificationQueryService;

import io.github.jhipster.service.filter.LongFilter;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.amc.careplanner.domain.Notification}.
 */
@RestController
@RequestMapping("/api/v1")
public class NotificationResourceExt extends NotificationResource{

    private final Logger log = LoggerFactory.getLogger(NotificationResourceExt.class);

    private static final String ENTITY_NAME = "notification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotificationServiceExt notificationServiceExt;

    private final NotificationQueryService notificationQueryService;
    
    private final UserRepositoryExt userRepositoryExt;
    
    private final S3Service  s3Service;
    
    private final SystemEventsHistoryServiceExt systemEventsHistoryServiceExt;

    public NotificationResourceExt(NotificationServiceExt notificationServiceExt, NotificationQueryService notificationQueryService, UserRepositoryExt userRepositoryExt, S3Service  s3Service, SystemEventsHistoryServiceExt systemEventsHistoryServiceExt) {
        super(notificationServiceExt,notificationQueryService);
    	this.notificationServiceExt = notificationServiceExt;
        this.notificationQueryService = notificationQueryService;
        this.userRepositoryExt = userRepositoryExt;
        this.s3Service = s3Service;
        this.systemEventsHistoryServiceExt = systemEventsHistoryServiceExt;
    }

    /**
     * {@code POST  /notifications} : Create a new notification.
     *
     * @param notificationDTO the notificationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notificationDTO, or with status {@code 400 (Bad Request)} if the notification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create-notification-by-client-id")
    public ResponseEntity<NotificationDTO> createNotification(@Valid @RequestBody NotificationDTO notificationDTO) throws URISyntaxException {
        log.debug("REST request to save Notification : {}", notificationDTO);
        if (notificationDTO.getId() != null) {
            throw new BadRequestAlertException("A new notification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
       //notificationDTO.setDateCreated(ZonedDateTime.now());
        notificationDTO.setLastUpdatedDate(ZonedDateTime.now());
        notificationDTO.setClientId(getClientIdFromLoggedInUser());
        NotificationDTO result = notificationServiceExt.save(notificationDTO);
        NotificationDTO result2 = result;
        NotificationDTO result3 = null;
  		if (notificationDTO.getImageContentType()!= null) {
  			String fileName = ENTITY_NAME + RandomUtil.generateRandomAlphaNum(10) + "-" + result.getId() + ".png";
  			String url = Constants.S3_ENDPOINT + fileName;
  			result.setImageUrl(url);
  			byte[] logoBytes = CommonUtils.resize(CommonUtils.createImageFromBytes(notificationDTO.getImage()),
  					Constants.FULL_IMAGE_HEIGHT, Constants.FULL_IMAGE_WIDTH);
  			CommonUtils.uploadToS3(logoBytes, fileName, s3Service.getAmazonS3(),notificationDTO.getImageContentType());
  			result2 = notificationServiceExt.save(result);
  			result2.setImage(null);
  			result2.setImageContentType(null);
  			 result3 = notificationServiceExt.save(result2);
  		} 
  		
  	  String loggedInAdminUserEmail = SecurityUtils.getCurrentUserLogin().get();
  			User loggedInAdminUser = userRepositoryExt.findOneByEmailIgnoreCase(loggedInAdminUserEmail).get();  		
  	  		CommonUtils.fireSystemEvent(systemEventsHistoryServiceExt, "createNotification", "/api/v1/create-notification-by-client-id",
  	        		result.getTitle() + " has just been created", "Notification", result.getId(), loggedInAdminUser.getId(),
  	        		loggedInAdminUser.getEmail(), result.getId());
        return ResponseEntity.created(new URI("/api/notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result3);
     }
   
    /**
     * {@code PUT  /notifications} : Updates an existing notification.
     *
     * @param notificationDTO the notificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificationDTO,
     * or with status {@code 400 (Bad Request)} if the notificationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update-notification-by-client-id")
    public ResponseEntity<NotificationDTO> updateNotification(@Valid @RequestBody NotificationDTO notificationDTO) throws URISyntaxException {
        log.debug("REST request to update Notification : {}", notificationDTO);
        if (notificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (notificationDTO != null && notificationDTO.getClientId() != null && notificationDTO.getClientId() != getClientIdFromLoggedInUser()) {
        	  throw new BadRequestAlertException("clientId mismatch", ENTITY_NAME, "clientIdMismatch");
        }
        notificationDTO.setLastUpdatedDate(ZonedDateTime.now());
        NotificationDTO result = notificationServiceExt.save(notificationDTO);
        NotificationDTO result2 = result;
        NotificationDTO result3 = null;
  		if (notificationDTO.getImageContentType()!= null) {
  			String fileName = ENTITY_NAME + RandomUtil.generateRandomAlphaNum(10) + "-" + result.getId() + ".png";
  			String url = Constants.S3_ENDPOINT + fileName;
  			result.setImageUrl(url);
  			byte[] logoBytes = CommonUtils.resize(CommonUtils.createImageFromBytes(notificationDTO.getImage()),
  					Constants.FULL_IMAGE_HEIGHT, Constants.FULL_IMAGE_WIDTH);
  			CommonUtils.uploadToS3(logoBytes, fileName, s3Service.getAmazonS3(),notificationDTO.getImageContentType());
  			result2 = notificationServiceExt.save(result);
  			result2.setImage(null);
  			result2.setImageContentType(null);
  			 result3 = notificationServiceExt.save(result2);
  		}
  		String loggedInAdminUserEmail = SecurityUtils.getCurrentUserLogin().get();
			User loggedInAdminUser = userRepositoryExt.findOneByEmailIgnoreCase(loggedInAdminUserEmail).get();  		
	  		CommonUtils.fireSystemEvent(systemEventsHistoryServiceExt, "updateNotification", "/api/v1/update-notification-by-client-id",
	        		result.getTitle() + " has just been created", "Notification", result.getId(), loggedInAdminUser.getId(),
	        		loggedInAdminUser.getEmail(), result.getId());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notificationDTO.getId().toString()))
            .body(result3);
    }

    /**
     * {@code GET  /notifications} : get all the notifications.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notifications in body.
     */
    @GetMapping("/get-all-notifications-by-client-id")
    public ResponseEntity<List<NotificationDTO>> getAllNotifications(NotificationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Notifications by criteria: {}", criteria);
        NotificationCriteria notificationCriteria = new NotificationCriteria();
		LongFilter longFilterForClientId = new LongFilter();
		longFilterForClientId.setEquals(getClientIdFromLoggedInUser());
		notificationCriteria.setClientId(longFilterForClientId);
        Page<NotificationDTO> page = notificationQueryService.findByCriteria(notificationCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /notifications/count} : count all the notifications.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/notifications/count")
    public ResponseEntity<Long> countNotifications(NotificationCriteria criteria) {
        log.debug("REST request to count Notifications by criteria: {}", criteria);
        return ResponseEntity.ok().body(notificationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /notifications/:id} : get the "id" notification.
     *
     * @param id the id of the notificationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notificationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/get-notification-by-client-id/{id}")
    public ResponseEntity<NotificationDTO> getNotification(@PathVariable Long id) {
        log.debug("REST request to get Notification : {}", id);
        Optional<NotificationDTO> notificationDTO = notificationServiceExt.findOne(id);
        return ResponseUtil.wrapOrNotFound(notificationDTO);
    }

    /**
     * {@code DELETE  /notifications/:id} : delete the "id" notification.
     *
     * @param id the id of the notificationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete-notification-by-client-id/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.COMPANY_ADMIN + "\")")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        log.debug("REST request to delete Notification : {}", id);
        notificationServiceExt.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
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
