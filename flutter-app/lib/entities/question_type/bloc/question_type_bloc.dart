import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/question_type/question_type_model.dart';
import 'package:amcCarePlanner/entities/question_type/question_type_repository.dart';
import 'package:amcCarePlanner/entities/question_type/bloc/question_type_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'question_type_events.dart';
part 'question_type_state.dart';

class QuestionTypeBloc extends Bloc<QuestionTypeEvent, QuestionTypeState> {
  final QuestionTypeRepository _questionTypeRepository;

  final questionTypeController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();

  QuestionTypeBloc({@required QuestionTypeRepository questionTypeRepository}) : assert(questionTypeRepository != null),
        _questionTypeRepository = questionTypeRepository, 
  super(QuestionTypeState(null,));

  @override
  void onTransition(Transition<QuestionTypeEvent, QuestionTypeState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<QuestionTypeState> mapEventToState(QuestionTypeEvent event) async* {
    if (event is InitQuestionTypeList) {
      yield* onInitList(event);
    } else if (event is QuestionTypeFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadQuestionTypeByIdForEdit) {
      yield* onLoadQuestionTypeIdForEdit(event);
    } else if (event is DeleteQuestionTypeById) {
      yield* onDeleteQuestionTypeId(event);
    } else if (event is LoadQuestionTypeByIdForView) {
      yield* onLoadQuestionTypeIdForView(event);
    }else if (event is QuestionTypeChanged){
      yield* onQuestionTypeChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }  }

  Stream<QuestionTypeState> onInitList(InitQuestionTypeList event) async* {
    yield this.state.copyWith(questionTypeStatusUI: QuestionTypeStatusUI.loading);
    List<QuestionType> questionTypes = await _questionTypeRepository.getAllQuestionTypes();
    yield this.state.copyWith(questionTypes: questionTypes, questionTypeStatusUI: QuestionTypeStatusUI.done);
  }

  Stream<QuestionTypeState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        QuestionType result;
        if(this.state.editMode) {
          QuestionType newQuestionType = QuestionType(state.loadedQuestionType.id,
            this.state.questionType.value,  
            this.state.lastUpdatedDate.value,  
          );

          result = await _questionTypeRepository.update(newQuestionType);
        } else {
          QuestionType newQuestionType = QuestionType(null,
            this.state.questionType.value,  
            this.state.lastUpdatedDate.value,  
          );

          result = await _questionTypeRepository.create(newQuestionType);
        }

        if (result == null) {
          yield this.state.copyWith(formStatus: FormzStatus.submissionFailure,
              generalNotificationKey: HttpUtils.badRequestServerKey);
        } else {
          yield this.state.copyWith(formStatus: FormzStatus.submissionSuccess,
              generalNotificationKey: HttpUtils.successResult);
        }
      } catch (e) {
        yield this.state.copyWith(formStatus: FormzStatus.submissionFailure,
            generalNotificationKey: HttpUtils.errorServerKey);
      }
    }
  }

  Stream<QuestionTypeState> onLoadQuestionTypeIdForEdit(LoadQuestionTypeByIdForEdit event) async* {
    yield this.state.copyWith(questionTypeStatusUI: QuestionTypeStatusUI.loading);
    QuestionType loadedQuestionType = await _questionTypeRepository.getQuestionType(event.id);

    final questionType = QuestionTypeInput.dirty(loadedQuestionType?.questionType != null ? loadedQuestionType.questionType: '');
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedQuestionType?.lastUpdatedDate != null ? loadedQuestionType.lastUpdatedDate: null);

    yield this.state.copyWith(loadedQuestionType: loadedQuestionType, editMode: true,
      questionType: questionType,
      lastUpdatedDate: lastUpdatedDate,
    questionTypeStatusUI: QuestionTypeStatusUI.done);

    questionTypeController.text = loadedQuestionType.questionType;
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedQuestionType?.lastUpdatedDate);
  }

  Stream<QuestionTypeState> onDeleteQuestionTypeId(DeleteQuestionTypeById event) async* {
    try {
      await _questionTypeRepository.delete(event.id);
      this.add(InitQuestionTypeList());
      yield this.state.copyWith(deleteStatus: QuestionTypeDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: QuestionTypeDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: QuestionTypeDeleteStatus.none);
  }

  Stream<QuestionTypeState> onLoadQuestionTypeIdForView(LoadQuestionTypeByIdForView event) async* {
    yield this.state.copyWith(questionTypeStatusUI: QuestionTypeStatusUI.loading);
    try {
      QuestionType loadedQuestionType = await _questionTypeRepository.getQuestionType(event.id);
      yield this.state.copyWith(loadedQuestionType: loadedQuestionType, questionTypeStatusUI: QuestionTypeStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedQuestionType: null, questionTypeStatusUI: QuestionTypeStatusUI.error);
    }
  }


  Stream<QuestionTypeState> onQuestionTypeChange(QuestionTypeChanged event) async* {
    final questionType = QuestionTypeInput.dirty(event.questionType);
    yield this.state.copyWith(
      questionType: questionType,
      formStatus: Formz.validate([
        questionType,
      this.state.lastUpdatedDate,
      ]),
    );
  }
  Stream<QuestionTypeState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.questionType,
        lastUpdatedDate,
      ]),
    );
  }

  @override
  Future<void> close() {
    questionTypeController.dispose();
    lastUpdatedDateController.dispose();
    return super.close();
  }

}