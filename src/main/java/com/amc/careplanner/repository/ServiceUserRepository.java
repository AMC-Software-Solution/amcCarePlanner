package com.amc.careplanner.repository;

import com.amc.careplanner.domain.ServiceUser;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ServiceUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceUserRepository extends JpaRepository<ServiceUser, Long>, JpaSpecificationExecutor<ServiceUser> {

    @Query("select serviceUser from ServiceUser serviceUser where serviceUser.user.login = ?#{principal.username}")
    List<ServiceUser> findByUserIsCurrentUser();
}
