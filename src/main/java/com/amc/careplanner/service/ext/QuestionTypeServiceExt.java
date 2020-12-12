package com.amc.careplanner.service.ext;

import com.amc.careplanner.domain.QuestionType;
import com.amc.careplanner.repository.QuestionTypeRepository;
import com.amc.careplanner.repository.ext.QuestionTypeRepositoryExt;
import com.amc.careplanner.service.QuestionTypeService;
import com.amc.careplanner.service.dto.QuestionTypeDTO;
import com.amc.careplanner.service.mapper.QuestionTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link QuestionType}.
 */
@Service
@Transactional
public class QuestionTypeServiceExt extends QuestionTypeService{

    private final Logger log = LoggerFactory.getLogger(QuestionTypeServiceExt.class);

    private final QuestionTypeRepositoryExt questionTypeRepositoryExt;

    private final QuestionTypeMapper questionTypeMapper;

    public QuestionTypeServiceExt(QuestionTypeRepositoryExt questionTypeRepositoryExt, QuestionTypeMapper questionTypeMapper) {
        super(questionTypeRepositoryExt,questionTypeMapper);
    	this.questionTypeRepositoryExt = questionTypeRepositoryExt;
        this.questionTypeMapper = questionTypeMapper;
    }

    /**
     * Save a questionType.
     *
     * @param questionTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public QuestionTypeDTO save(QuestionTypeDTO questionTypeDTO) {
        log.debug("Request to save QuestionType : {}", questionTypeDTO);
        QuestionType questionType = questionTypeMapper.toEntity(questionTypeDTO);
        questionType = questionTypeRepositoryExt.save(questionType);
        return questionTypeMapper.toDto(questionType);
    }

    /**
     * Get all the questionTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<QuestionTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all QuestionTypes");
        return questionTypeRepositoryExt.findAll(pageable)
            .map(questionTypeMapper::toDto);
    }


    /**
     * Get one questionType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<QuestionTypeDTO> findOne(Long id) {
        log.debug("Request to get QuestionType : {}", id);
        return questionTypeRepositoryExt.findById(id)
            .map(questionTypeMapper::toDto);
    }

    /**
     * Delete the questionType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete QuestionType : {}", id);
        questionTypeRepositoryExt.deleteById(id);
    }
}
