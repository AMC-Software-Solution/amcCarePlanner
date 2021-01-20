import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/answer/answer_model.dart';
import 'package:amcCarePlanner/entities/answer/answer_repository.dart';
import 'package:amcCarePlanner/entities/answer/bloc/answer_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'answer_events.dart';
part 'answer_state.dart';

class AnswerBloc extends Bloc<AnswerEvent, AnswerState> {
  final AnswerRepository _answerRepository;

  final answerController = TextEditingController();
  final descriptionController = TextEditingController();
  final attribute1Controller = TextEditingController();
  final attribute2Controller = TextEditingController();
  final attribute3Controller = TextEditingController();
  final attribute4Controller = TextEditingController();
  final attribute5Controller = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  AnswerBloc({@required AnswerRepository answerRepository}) : assert(answerRepository != null),
        _answerRepository = answerRepository, 
  super(AnswerState(null,null,));

  @override
  void onTransition(Transition<AnswerEvent, AnswerState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<AnswerState> mapEventToState(AnswerEvent event) async* {
    if (event is InitAnswerList) {
      yield* onInitList(event);
    } else if (event is AnswerFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadAnswerByIdForEdit) {
      yield* onLoadAnswerIdForEdit(event);
    } else if (event is DeleteAnswerById) {
      yield* onDeleteAnswerId(event);
    } else if (event is LoadAnswerByIdForView) {
      yield* onLoadAnswerIdForView(event);
    }else if (event is AnswerChanged){
      yield* onAnswerChange(event);
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
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<AnswerState> onInitList(InitAnswerList event) async* {
    yield this.state.copyWith(answerStatusUI: AnswerStatusUI.loading);
    List<Answer> answers = await _answerRepository.getAllAnswers();
    yield this.state.copyWith(answers: answers, answerStatusUI: AnswerStatusUI.done);
  }

  Stream<AnswerState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        Answer result;
        if(this.state.editMode) {
          Answer newAnswer = Answer(state.loadedAnswer.id,
            this.state.answer.value,  
            this.state.description.value,  
            this.state.attribute1.value,  
            this.state.attribute2.value,  
            this.state.attribute3.value,  
            this.state.attribute4.value,  
            this.state.attribute5.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
            null, 
          );

          result = await _answerRepository.update(newAnswer);
        } else {
          Answer newAnswer = Answer(null,
            this.state.answer.value,  
            this.state.description.value,  
            this.state.attribute1.value,  
            this.state.attribute2.value,  
            this.state.attribute3.value,  
            this.state.attribute4.value,  
            this.state.attribute5.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
            null, 
          );

          result = await _answerRepository.create(newAnswer);
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

  Stream<AnswerState> onLoadAnswerIdForEdit(LoadAnswerByIdForEdit event) async* {
    yield this.state.copyWith(answerStatusUI: AnswerStatusUI.loading);
    Answer loadedAnswer = await _answerRepository.getAnswer(event.id);

    final answer = AnswerInput.dirty(loadedAnswer?.answer != null ? loadedAnswer.answer: '');
    final description = DescriptionInput.dirty(loadedAnswer?.description != null ? loadedAnswer.description: '');
    final attribute1 = Attribute1Input.dirty(loadedAnswer?.attribute1 != null ? loadedAnswer.attribute1: '');
    final attribute2 = Attribute2Input.dirty(loadedAnswer?.attribute2 != null ? loadedAnswer.attribute2: '');
    final attribute3 = Attribute3Input.dirty(loadedAnswer?.attribute3 != null ? loadedAnswer.attribute3: '');
    final attribute4 = Attribute4Input.dirty(loadedAnswer?.attribute4 != null ? loadedAnswer.attribute4: '');
    final attribute5 = Attribute5Input.dirty(loadedAnswer?.attribute5 != null ? loadedAnswer.attribute5: '');
    final createdDate = CreatedDateInput.dirty(loadedAnswer?.createdDate != null ? loadedAnswer.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedAnswer?.lastUpdatedDate != null ? loadedAnswer.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedAnswer?.clientId != null ? loadedAnswer.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedAnswer?.hasExtraData != null ? loadedAnswer.hasExtraData: false);

    yield this.state.copyWith(loadedAnswer: loadedAnswer, editMode: true,
      answer: answer,
      description: description,
      attribute1: attribute1,
      attribute2: attribute2,
      attribute3: attribute3,
      attribute4: attribute4,
      attribute5: attribute5,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    answerStatusUI: AnswerStatusUI.done);

    answerController.text = loadedAnswer.answer;
    descriptionController.text = loadedAnswer.description;
    attribute1Controller.text = loadedAnswer.attribute1;
    attribute2Controller.text = loadedAnswer.attribute2;
    attribute3Controller.text = loadedAnswer.attribute3;
    attribute4Controller.text = loadedAnswer.attribute4;
    attribute5Controller.text = loadedAnswer.attribute5;
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedAnswer?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedAnswer?.lastUpdatedDate);
    clientIdController.text = loadedAnswer.clientId.toString();
  }

  Stream<AnswerState> onDeleteAnswerId(DeleteAnswerById event) async* {
    try {
      await _answerRepository.delete(event.id);
      this.add(InitAnswerList());
      yield this.state.copyWith(deleteStatus: AnswerDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: AnswerDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: AnswerDeleteStatus.none);
  }

  Stream<AnswerState> onLoadAnswerIdForView(LoadAnswerByIdForView event) async* {
    yield this.state.copyWith(answerStatusUI: AnswerStatusUI.loading);
    try {
      Answer loadedAnswer = await _answerRepository.getAnswer(event.id);
      yield this.state.copyWith(loadedAnswer: loadedAnswer, answerStatusUI: AnswerStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedAnswer: null, answerStatusUI: AnswerStatusUI.error);
    }
  }


  Stream<AnswerState> onAnswerChange(AnswerChanged event) async* {
    final answer = AnswerInput.dirty(event.answer);
    yield this.state.copyWith(
      answer: answer,
      formStatus: Formz.validate([
        answer,
      this.state.description,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<AnswerState> onDescriptionChange(DescriptionChanged event) async* {
    final description = DescriptionInput.dirty(event.description);
    yield this.state.copyWith(
      description: description,
      formStatus: Formz.validate([
      this.state.answer,
        description,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<AnswerState> onAttribute1Change(Attribute1Changed event) async* {
    final attribute1 = Attribute1Input.dirty(event.attribute1);
    yield this.state.copyWith(
      attribute1: attribute1,
      formStatus: Formz.validate([
      this.state.answer,
      this.state.description,
        attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<AnswerState> onAttribute2Change(Attribute2Changed event) async* {
    final attribute2 = Attribute2Input.dirty(event.attribute2);
    yield this.state.copyWith(
      attribute2: attribute2,
      formStatus: Formz.validate([
      this.state.answer,
      this.state.description,
      this.state.attribute1,
        attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<AnswerState> onAttribute3Change(Attribute3Changed event) async* {
    final attribute3 = Attribute3Input.dirty(event.attribute3);
    yield this.state.copyWith(
      attribute3: attribute3,
      formStatus: Formz.validate([
      this.state.answer,
      this.state.description,
      this.state.attribute1,
      this.state.attribute2,
        attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<AnswerState> onAttribute4Change(Attribute4Changed event) async* {
    final attribute4 = Attribute4Input.dirty(event.attribute4);
    yield this.state.copyWith(
      attribute4: attribute4,
      formStatus: Formz.validate([
      this.state.answer,
      this.state.description,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
        attribute4,
      this.state.attribute5,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<AnswerState> onAttribute5Change(Attribute5Changed event) async* {
    final attribute5 = Attribute5Input.dirty(event.attribute5);
    yield this.state.copyWith(
      attribute5: attribute5,
      formStatus: Formz.validate([
      this.state.answer,
      this.state.description,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
        attribute5,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<AnswerState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.answer,
      this.state.description,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<AnswerState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.answer,
      this.state.description,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<AnswerState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.answer,
      this.state.description,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<AnswerState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.answer,
      this.state.description,
      this.state.attribute1,
      this.state.attribute2,
      this.state.attribute3,
      this.state.attribute4,
      this.state.attribute5,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    answerController.dispose();
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