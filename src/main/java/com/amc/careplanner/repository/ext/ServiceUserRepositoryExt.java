package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.ServiceUser;
import com.amc.careplanner.repository.ServiceUserRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ServiceUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceUserRepositoryExt extends  ServiceUserRepository{

    @Query("select serviceUser from ServiceUser serviceUser where serviceUser.user.login = ?#{principal.username}")
    List<ServiceUser> findByUserIsCurrentUser();
}
