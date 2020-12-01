package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.AnswerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Answer} and its DTO {@link AnswerDTO}.
 */
@Mapper(componentModel = "spring", uses = {QuestionMapper.class, ServiceUserMapper.class, EmployeeMapper.class})
public interface AnswerMapper extends EntityMapper<AnswerDTO, Answer> {

    @Mapping(source = "question.id", target = "questionId")
    @Mapping(source = "question.question", target = "questionQuestion")
    @Mapping(source = "serviceUser.id", target = "serviceUserId")
    @Mapping(source = "serviceUser.serviceUserCode", target = "serviceUserServiceUserCode")
    @Mapping(source = "recordedBy.id", target = "recordedById")
    @Mapping(source = "recordedBy.employeeCode", target = "recordedByEmployeeCode")
    AnswerDTO toDto(Answer answer);

    @Mapping(source = "questionId", target = "question")
    @Mapping(source = "serviceUserId", target = "serviceUser")
    @Mapping(source = "recordedById", target = "recordedBy")
    Answer toEntity(AnswerDTO answerDTO);

    default Answer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Answer answer = new Answer();
        answer.setId(id);
        return answer;
    }
}
