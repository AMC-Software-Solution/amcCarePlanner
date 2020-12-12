package com.amc.careplanner.service.ext;

import com.amc.careplanner.domain.Notification;
import com.amc.careplanner.repository.NotificationRepository;
import com.amc.careplanner.repository.ext.NotificationRepositoryExt;
import com.amc.careplanner.service.NotificationService;
import com.amc.careplanner.service.dto.NotificationDTO;
import com.amc.careplanner.service.mapper.NotificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Notification}.
 */
@Service
@Transactional
public class NotificationServiceExt extends NotificationService{

    private final Logger log = LoggerFactory.getLogger(NotificationServiceExt.class);

    private final NotificationRepositoryExt notificationRepositoryExt;

    private final NotificationMapper notificationMapper;

    public NotificationServiceExt(NotificationRepositoryExt notificationRepositoryExt, NotificationMapper notificationMapper) {
        super(notificationRepositoryExt,notificationMapper);
    	this.notificationRepositoryExt = notificationRepositoryExt;
        this.notificationMapper = notificationMapper;
    }

    /**
     * Save a notification.
     *
     * @param notificationDTO the entity to save.
     * @return the persisted entity.
     */
    public NotificationDTO save(NotificationDTO notificationDTO) {
        log.debug("Request to save Notification : {}", notificationDTO);
        Notification notification = notificationMapper.toEntity(notificationDTO);
        notification = notificationRepositoryExt.save(notification);
        return notificationMapper.toDto(notification);
    }

    /**
     * Get all the notifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NotificationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Notifications");
        return notificationRepositoryExt.findAll(pageable)
            .map(notificationMapper::toDto);
    }


    /**
     * Get one notification by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NotificationDTO> findOne(Long id) {
        log.debug("Request to get Notification : {}", id);
        return notificationRepositoryExt.findById(id)
            .map(notificationMapper::toDto);
    }

    /**
     * Delete the notification by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Notification : {}", id);
        notificationRepositoryExt.deleteById(id);
    }
}
