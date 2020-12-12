package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.Task;
import com.amc.careplanner.repository.TaskRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Task entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskRepositoryExt extends TaskRepository{
}
