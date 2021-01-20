part of 'question_bloc.dart';

enum QuestionStatusUI {init, loading, error, done}
enum QuestionDeleteStatus {ok, ko, none}

class QuestionState extends Equatable {
  final List<Question> questions;
  final Question loadedQuestion;
  final bool editMode;
  final QuestionDeleteStatus deleteStatus;
  final QuestionStatusUI questionStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final QuestionInput question;
  final DescriptionInput description;
  final Attribute1Input attribute1;
  final Attribute2Input attribute2;
  final Attribute3Input attribute3;
  final Attribute4Input attribute4;
  final Attribute5Input attribute5;
  final OptionalInput optional;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  QuestionState(
CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.questions = const [],
    this.questionStatusUI = QuestionStatusUI.init,
    this.loadedQuestion = const Question(0,'','','','','','','',false,null,null,0,false,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = QuestionDeleteStatus.none,
    this.question = const QuestionInput.pure(),
    this.description = const DescriptionInput.pure(),
    this.attribute1 = const Attribute1Input.pure(),
    this.attribute2 = const Attribute2Input.pure(),
    this.attribute3 = const Attribute3Input.pure(),
    this.attribute4 = const Attribute4Input.pure(),
    this.attribute5 = const Attribute5Input.pure(),
    this.optional = const OptionalInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  QuestionState copyWith({
    List<Question> questions,
    QuestionStatusUI questionStatusUI,
    bool editMode,
    QuestionDeleteStatus deleteStatus,
    Question loadedQuestion,
    FormzStatus formStatus,
    String generalNotificationKey,
    QuestionInput question,
    DescriptionInput description,
    Attribute1Input attribute1,
    Attribute2Input attribute2,
    Attribute3Input attribute3,
    Attribute4Input attribute4,
    Attribute5Input attribute5,
    OptionalInput optional,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return QuestionState(
        createdDate,
        lastUpdatedDate,
      questions: questions ?? this.questions,
      questionStatusUI: questionStatusUI ?? this.questionStatusUI,
      loadedQuestion: loadedQuestion ?? this.loadedQuestion,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      question: question ?? this.question,
      description: description ?? this.description,
      attribute1: attribute1 ?? this.attribute1,
      attribute2: attribute2 ?? this.attribute2,
      attribute3: attribute3 ?? this.attribute3,
      attribute4: attribute4 ?? this.attribute4,
      attribute5: attribute5 ?? this.attribute5,
      optional: optional ?? this.optional,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [questions, questionStatusUI,
     loadedQuestion, editMode, deleteStatus, formStatus, generalNotificationKey, 
question,description,attribute1,attribute2,attribute3,attribute4,attribute5,optional,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
