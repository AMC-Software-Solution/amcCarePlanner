package com.amc.careplanner.repository;

import com.amc.careplanner.domain.SystemEventsHistory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the SystemEventsHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemEventsHistoryRepository extends JpaRepository<SystemEventsHistory, Long>, JpaSpecificationExecutor<SystemEventsHistory> {

    @Query("select systemEventsHistory from SystemEventsHistory systemEventsHistory where systemEventsHistory.triggedBy.login = ?#{principal.username}")
    List<SystemEventsHistory> findByTriggedByIsCurrentUser();
}
