package com.amc.careplanner.service.ext;

import com.amc.careplanner.domain.Question;
import com.amc.careplanner.repository.QuestionRepository;
import com.amc.careplanner.repository.ext.QuestionRepositoryExt;
import com.amc.careplanner.service.QuestionService;
import com.amc.careplanner.service.dto.QuestionDTO;
import com.amc.careplanner.service.mapper.QuestionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Question}.
 */
@Service
@Transactional
public class QuestionServiceExt extends QuestionService {

    private final Logger log = LoggerFactory.getLogger(QuestionServiceExt.class);

    private final QuestionRepositoryExt questionRepositoryExt;

    private final QuestionMapper questionMapper;

    public QuestionServiceExt(QuestionRepositoryExt questionRepositoryExt, QuestionMapper questionMapper) {
        super(questionRepositoryExt,questionMapper);
    	this.questionRepositoryExt = questionRepositoryExt;
        this.questionMapper = questionMapper;
    }

    
}
