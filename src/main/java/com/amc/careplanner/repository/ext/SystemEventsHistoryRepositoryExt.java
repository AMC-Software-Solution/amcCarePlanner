package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.SystemEventsHistory;
import com.amc.careplanner.repository.SystemEventsHistoryRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the SystemEventsHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemEventsHistoryRepositoryExt extends SystemEventsHistoryRepository{

    @Query("select systemEventsHistory from SystemEventsHistory systemEventsHistory where systemEventsHistory.triggedBy.login = ?#{principal.username}")
    List<SystemEventsHistory> findByTriggedByIsCurrentUser();
}
