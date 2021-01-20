part of 'answer_bloc.dart';

enum AnswerStatusUI {init, loading, error, done}
enum AnswerDeleteStatus {ok, ko, none}

class AnswerState extends Equatable {
  final List<Answer> answers;
  final Answer loadedAnswer;
  final bool editMode;
  final AnswerDeleteStatus deleteStatus;
  final AnswerStatusUI answerStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final AnswerInput answer;
  final DescriptionInput description;
  final Attribute1Input attribute1;
  final Attribute2Input attribute2;
  final Attribute3Input attribute3;
  final Attribute4Input attribute4;
  final Attribute5Input attribute5;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  AnswerState(
CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.answers = const [],
    this.answerStatusUI = AnswerStatusUI.init,
    this.loadedAnswer = const Answer(0,'','','','','','','',null,null,0,false,null,null,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = AnswerDeleteStatus.none,
    this.answer = const AnswerInput.pure(),
    this.description = const DescriptionInput.pure(),
    this.attribute1 = const Attribute1Input.pure(),
    this.attribute2 = const Attribute2Input.pure(),
    this.attribute3 = const Attribute3Input.pure(),
    this.attribute4 = const Attribute4Input.pure(),
    this.attribute5 = const Attribute5Input.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  AnswerState copyWith({
    List<Answer> answers,
    AnswerStatusUI answerStatusUI,
    bool editMode,
    AnswerDeleteStatus deleteStatus,
    Answer loadedAnswer,
    FormzStatus formStatus,
    String generalNotificationKey,
    AnswerInput answer,
    DescriptionInput description,
    Attribute1Input attribute1,
    Attribute2Input attribute2,
    Attribute3Input attribute3,
    Attribute4Input attribute4,
    Attribute5Input attribute5,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return AnswerState(
        createdDate,
        lastUpdatedDate,
      answers: answers ?? this.answers,
      answerStatusUI: answerStatusUI ?? this.answerStatusUI,
      loadedAnswer: loadedAnswer ?? this.loadedAnswer,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      answer: answer ?? this.answer,
      description: description ?? this.description,
      attribute1: attribute1 ?? this.attribute1,
      attribute2: attribute2 ?? this.attribute2,
      attribute3: attribute3 ?? this.attribute3,
      attribute4: attribute4 ?? this.attribute4,
      attribute5: attribute5 ?? this.attribute5,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [answers, answerStatusUI,
     loadedAnswer, editMode, deleteStatus, formStatus, generalNotificationKey, 
answer,description,attribute1,attribute2,attribute3,attribute4,attribute5,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
