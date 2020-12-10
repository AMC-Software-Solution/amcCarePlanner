package com.amc.careplanner.service.ext;

import com.amc.careplanner.domain.Answer;
import com.amc.careplanner.repository.AnswerRepository;
import com.amc.careplanner.repository.ext.AnswerRepositoryExt;
import com.amc.careplanner.service.AnswerService;
import com.amc.careplanner.service.dto.AnswerDTO;
import com.amc.careplanner.service.mapper.AnswerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Answer}.
 */
@Service
@Transactional
public class AnswerServiceExt extends AnswerService {

    private final Logger log = LoggerFactory.getLogger(AnswerServiceExt.class);

    private final AnswerRepositoryExt answerRepositoryExt;

    private final AnswerMapper answerMapper;

    public AnswerServiceExt(AnswerRepositoryExt answerRepository, AnswerMapper answerMapper) {
    	super(answerRepository,answerMapper);
        this.answerRepositoryExt = answerRepository;
        this.answerMapper = answerMapper;
    }

    /**
     * Save a answer.
     *
     * @param answerDTO the entity to save.
     * @return the persisted entity.
     */
    public AnswerDTO save(AnswerDTO answerDTO) {
        log.debug("Request to save Answer : {}", answerDTO);
        Answer answer = answerMapper.toEntity(answerDTO);
        answer = answerRepositoryExt.save(answer);
        return answerMapper.toDto(answer);
    }

    /**
     * Get all the answers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AnswerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Answers");
        return answerRepositoryExt.findAll(pageable)
            .map(answerMapper::toDto);
    }


    /**
     * Get one answer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnswerDTO> findOne(Long id) {
        log.debug("Request to get Answer : {}", id);
        return answerRepositoryExt.findById(id)
            .map(answerMapper::toDto);
    }

    /**
     * Delete the answer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Answer : {}", id);
        answerRepositoryExt.deleteById(id);
    }
}
