package com.amc.careplanner.service.ext;

import com.amc.careplanner.domain.Task;
import com.amc.careplanner.repository.TaskRepository;
import com.amc.careplanner.repository.ext.TaskRepositoryExt;
import com.amc.careplanner.service.TaskService;
import com.amc.careplanner.service.dto.TaskDTO;
import com.amc.careplanner.service.mapper.TaskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Task}.
 */
@Service
@Transactional
public class TaskServiceExt extends TaskService{

    private final Logger log = LoggerFactory.getLogger(TaskServiceExt.class);

    private final TaskRepositoryExt taskRepositoryExt;

    private final TaskMapper taskMapper;

    public TaskServiceExt(TaskRepositoryExt taskRepositoryExt, TaskMapper taskMapper) {
        super(taskRepositoryExt,taskMapper);
    	this.taskRepositoryExt = taskRepositoryExt;
        this.taskMapper = taskMapper;
    }

    /**
     * Save a task.
     *
     * @param taskDTO the entity to save.
     * @return the persisted entity.
     */
    public TaskDTO save(TaskDTO taskDTO) {
        log.debug("Request to save Task : {}", taskDTO);
        Task task = taskMapper.toEntity(taskDTO);
        task = taskRepositoryExt.save(task);
        return taskMapper.toDto(task);
    }

    /**
     * Get all the tasks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tasks");
        return taskRepositoryExt.findAll(pageable)
            .map(taskMapper::toDto);
    }


    /**
     * Get one task by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaskDTO> findOne(Long id) {
        log.debug("Request to get Task : {}", id);
        return taskRepositoryExt.findById(id)
            .map(taskMapper::toDto);
    }

    /**
     * Delete the task by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Task : {}", id);
        taskRepositoryExt.deleteById(id);
    }
}
