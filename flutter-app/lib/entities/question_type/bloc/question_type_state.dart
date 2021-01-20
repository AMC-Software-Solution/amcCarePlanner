part of 'question_type_bloc.dart';

enum QuestionTypeStatusUI {init, loading, error, done}
enum QuestionTypeDeleteStatus {ok, ko, none}

class QuestionTypeState extends Equatable {
  final List<QuestionType> questionTypes;
  final QuestionType loadedQuestionType;
  final bool editMode;
  final QuestionTypeDeleteStatus deleteStatus;
  final QuestionTypeStatusUI questionTypeStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final QuestionTypeInput questionType;
  final LastUpdatedDateInput lastUpdatedDate;

  
  QuestionTypeState(
LastUpdatedDateInput lastUpdatedDate,{
    this.questionTypes = const [],
    this.questionTypeStatusUI = QuestionTypeStatusUI.init,
    this.loadedQuestionType = const QuestionType(0,'',null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = QuestionTypeDeleteStatus.none,
    this.questionType = const QuestionTypeInput.pure(),
  }):this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  QuestionTypeState copyWith({
    List<QuestionType> questionTypes,
    QuestionTypeStatusUI questionTypeStatusUI,
    bool editMode,
    QuestionTypeDeleteStatus deleteStatus,
    QuestionType loadedQuestionType,
    FormzStatus formStatus,
    String generalNotificationKey,
    QuestionTypeInput questionType,
    LastUpdatedDateInput lastUpdatedDate,
  }) {
    return QuestionTypeState(
        lastUpdatedDate,
      questionTypes: questionTypes ?? this.questionTypes,
      questionTypeStatusUI: questionTypeStatusUI ?? this.questionTypeStatusUI,
      loadedQuestionType: loadedQuestionType ?? this.loadedQuestionType,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      questionType: questionType ?? this.questionType,
    );
  }

  @override
  List<Object> get props => [questionTypes, questionTypeStatusUI,
     loadedQuestionType, editMode, deleteStatus, formStatus, generalNotificationKey, 
questionType,lastUpdatedDate,];

  @override
  bool get stringify => true;
}
