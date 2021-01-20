part of 'question_type_bloc.dart';

abstract class QuestionTypeEvent extends Equatable {
  const QuestionTypeEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitQuestionTypeList extends QuestionTypeEvent {}

class QuestionTypeChanged extends QuestionTypeEvent {
  final String questionType;
  
  const QuestionTypeChanged({@required this.questionType});
  
  @override
  List<Object> get props => [questionType];
}
class LastUpdatedDateChanged extends QuestionTypeEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}

class QuestionTypeFormSubmitted extends QuestionTypeEvent {}

class LoadQuestionTypeByIdForEdit extends QuestionTypeEvent {
  final int id;

  const LoadQuestionTypeByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteQuestionTypeById extends QuestionTypeEvent {
  final int id;

  const DeleteQuestionTypeById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadQuestionTypeByIdForView extends QuestionTypeEvent {
  final int id;

  const LoadQuestionTypeByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
