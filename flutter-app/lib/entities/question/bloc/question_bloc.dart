import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/question/question_model.dart';
import 'package:amcCarePlanner/entities/question/question_repository.dart';
import 'package:amcCarePlanner/entities/question/bloc/question_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'question_events.dart';
part 'question_state.dart';

class QuestionBloc extends Bloc<QuestionEvent, QuestionState> {
  final QuestionRepository _questionRepository;

  final questionController = TextEditingController();
  final descriptionController = TextEditingController();
  final attribute1Controller = TextEditingController();
  final attribute2Controller = TextEditingController();
  final attribute3Controller = TextEditingController();
  final attribute4Controller = TextEditingController();
  final attribute5Controller = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  QuestionBloc({@required QuestionRepository questionRepository}) : assert(questionRepository != null),
        _questionRepository = questionRepository, 
  super(QuestionState(null,null,));

  @override
  void onTransition(Transition<QuestionEvent, QuestionState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<QuestionState> mapEventToState(QuestionEvent event) async* {
    if (event is InitQuestionList) {
      yield* onInitList(event);
    } else if (event is QuestionFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadQuestionByIdForEdit) {
      yield* onLoadQuestionIdForEdit(event);
    } else if (event is DeleteQuestionById) {
      yield* onDeleteQuestionId(event);
    } else if (event is LoadQuestionByIdForView) {
      yield* onLoadQuestionIdForView(event);
    }else if (event is QuestionChanged){
      yield* onQuestionChange(event);
    }else if (event is DescriptionChanged){
      yield* onDescriptionChange(event);
    }else if (event is Attribute1Changed){
      yield* onAttribute1Change(event);
    }else if (event is Attribute2Changed){
      yield* onAttribute2Change(event);
    }else if (event is Attribute3Changed){
      yield* onAttribute3Change(event);
    }else if (event is Attribute4Changed){
      yield* onAttribute4Change(event);
    }else if (event is Attribute5Changed){
      yield* onAttribute5Change(event);
    }else if (event is OptionalChanged){
      yield* onOptionalChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<QuestionState> onInitList(InitQuestionList event) async* {
    yield this.state.copyWith(questionStatusUI: QuestionStatusUI.loading);
    List<Question> questions = await _questionRepository.getAllQuestions();
    yield this.state.copyWith(questions: questions, questionStatusUI: QuestionStatusUI.done);
  }

  Stream<QuestionState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        Question result;
        if(this.state.editMode) {
          Question newQuestion = Question(state.loadedQuestion.id,
            this.state.question.value,  
            this.state.description.value,  
            this.state.attribute1.value,  
            this.state.attribute2.value,  
            this.state.attribute3.value,  
            this.state.attribute4.value,  
            this.state.attribute5.value,  
            this.state.optional.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
          );

          result = await _questionRepository.update(newQuestion);
        } else {
          Question newQuestion = Question(null,
            this.state.question.value,  
            this.state.description.value,  
            this.state.attribute1.value,  
            this.state.attribute2.value,  
            this.state.attribute3.value,  
            this.state.attribute4.value,  
            this.state.attribute5.value,  
            this.state.optional.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
          );

          result = await _questionRepository.create(newQuestion);
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

  Stream<QuestionState> onLoadQuestionIdForEdit(LoadQuestionByIdForEdit event) async* {
    yield this.state.copyWith(questionStatusUI: QuestionStatusUI.loading);
    Question loadedQuestion = await _questionRepository.getQuestion(event.id);

    final question = QuestionInput.dirty(loadedQuestion?.question != null ? loadedQuestion.question: '');
    final description = DescriptionInput.dirty(loadedQuestion?.description != null ? loadedQuestion.description: '');
    final attribute1 = Attribute1Input.dirty(loadedQuestion?.attribute1 != null ? loadedQuestion.attribute1: '');
    final attribute2 = Attribute2Input.dirty(loadedQuestion?.attribute2 != null ? loadedQuestion.attribute2: '');
    final attribute3 = Attribute3Input.dirty(loadedQuestion?.attribute3 != null ? loadedQuestion.attribute3: '');
    final attribute4 = Attribute4Input.dirty(loadedQuestion?.attribute4 != null ? loadedQuestion.attribute4: '');
    final attribute5 = Attribute5Input.dirty(loadedQuestion?.attribute5 != null ? loadedQuestion.attribute5: '');
    final optional = OptionalInput.dirty(loadedQuestion?.optional != null ? loadedQuestion.optional: false);
    final createdDate = CreatedDateInput.dirty(loadedQuestion?.createdDate != null ? loadedQuestion.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedQuestion?.lastUpdatedDate != null ? loadedQuestion.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedQuestion?.clientId != null ? loadedQuestion.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedQuestion?.hasExtraData != null ? loadedQuestion.hasExtraData: false);

    yield this.state.copyWith(loadedQuestion: loadedQuestion, editMode: true,
      question: question,
      description: description,
      attribute1: attribute1,
      attribute2: attribute2,
      attribute3: attribute3,
      attribute4: attribute4,
      attribute5: attribute5,
      optional: optional,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    questionStatusUI: QuestionStatusUI.done);

    questionController.text = loadedQuestion.question;
    descriptionController.text = loadedQuestion.description;
    attribute1Controller.text = loadedQuestion.attribute1;
    attribute2Controller.text = loadedQuestion.attribute2;
    attribute3Controller.text = loadedQuestion.attribute3;
    attribute4Controller.text = loadedQuestion.attribute4;
    attribute5Controller.text = loadedQuestion.attribute5;
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedQuestion?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedQuestion?.lastUpdatedDate);
    clientIdController.text = loadedQuestion.clientId.toString();
  }

  Stream<QuestionState> onDeleteQuestionId(DeleteQuestionById event) async* {
    try {
      await _questionRepository.delete(event.id);
      this.add(InitQuestionList());
      yield this.state.copyWith(deleteStatus: QuestionDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: QuestionDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: QuestionDeleteStatus.none);
  }

  Stream<QuestionState> onLoadQuestionIdForView(LoadQuestionByIdForView event) async* {
    yield this.state.copyWith(questionStatusUI: QuestionStatusUI.loading);
    try {
      Question loadedQuestion = await _questionRepository.getQuestion(event.id);
      yield this.state.copyWith(loadedQuestion: loadedQuestion, questionStatusUI: QuestionStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedQuestion: null, questionStatusUI: QuestionStatusUI.error);
    }
  }


  Stream<QuestionState> onQuestionChange(QuestionChanged event) async* {
    final question = QuestionInput.dirty(event.question);
    yield this.state.copyWith(
      question: question,
      formStatus: Formz.validate([
        question,
      this.state.description,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.optional,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<QuestionState> onDescriptionChange(DescriptionChanged event) async* {
    final description = DescriptionInput.dirty(event.description);
    yield this.state.copyWith(
      description: description,
      formStatus: Formz.validate([
      this.state.question,
        description,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.optional,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<QuestionState> onAttribute1Change(Attribute1Changed event) async* {
    final attribute1 = Attribute1Input.dirty(event.attribute1);
    yield this.state.copyWith(
      attribute1: attribute1,
      formStatus: Formz.validate([
      this.state.question,
      this.state.description,
        attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.optional,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<QuestionState> onAttribute2Change(Attribute2Changed event) async* {
    final attribute2 = Attribute2Input.dirty(event.attribute2);
    yield this.state.copyWith(
      attribute2: attribute2,
      formStatus: Formz.validate([
      this.state.question,
      this.state.description,
      this.state.attribute1,
        attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.optional,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<QuestionState> onAttribute3Change(Attribute3Changed event) async* {
    final attribute3 = Attribute3Input.dirty(event.attribute3);
    yield this.state.copyWith(
      attribute3: attribute3,
      formStatus: Formz.validate([
      this.state.question,
      this.state.description,
      this.state.attribute1,
      this.state.attribute2,
        attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.optional,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<QuestionState> onAttribute4Change(Attribute4Changed event) async* {
    final attribute4 = Attribute4Input.dirty(event.attribute4);
    yield this.state.copyWith(
      attribute4: attribute4,
      formStatus: Formz.validate([
      this.state.question,
      this.state.description,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
        attribute4,
      this.state.attribute5,
      this.state.optional,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<QuestionState> onAttribute5Change(Attribute5Changed event) async* {
    final attribute5 = Attribute5Input.dirty(event.attribute5);
    yield this.state.copyWith(
      attribute5: attribute5,
      formStatus: Formz.validate([
      this.state.question,
      this.state.description,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
        attribute5,
      this.state.optional,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<QuestionState> onOptionalChange(OptionalChanged event) async* {
    final optional = OptionalInput.dirty(event.optional);
    yield this.state.copyWith(
      optional: optional,
      formStatus: Formz.validate([
      this.state.question,
      this.state.description,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
        optional,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<QuestionState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.question,
      this.state.description,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.optional,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<QuestionState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.question,
      this.state.description,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.optional,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<QuestionState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.question,
      this.state.description,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.optional,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<QuestionState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.question,
      this.state.description,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.optional,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    questionController.dispose();
    descriptionController.dispose();
    attribute1Controller.dispose();
    attribute2Controller.dispose();
    attribute3Controller.dispose();
    attribute4Controller.dispose();
    attribute5Controller.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}