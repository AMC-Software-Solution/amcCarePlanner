package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.Notification;
import com.amc.careplanner.repository.NotificationRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Notification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificationRepositoryExt extends NotificationRepository{
}
