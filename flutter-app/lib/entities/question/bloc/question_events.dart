part of 'question_bloc.dart';

abstract class QuestionEvent extends Equatable {
  const QuestionEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitQuestionList extends QuestionEvent {}

class QuestionChanged extends QuestionEvent {
  final String question;
  
  const QuestionChanged({@required this.question});
  
  @override
  List<Object> get props => [question];
}
class DescriptionChanged extends QuestionEvent {
  final String description;
  
  const DescriptionChanged({@required this.description});
  
  @override
  List<Object> get props => [description];
}
class Attribute1Changed extends QuestionEvent {
  final String attribute1;
  
  const Attribute1Changed({@required this.attribute1});
  
  @override
  List<Object> get props => [attribute1];
}
class Attribute2Changed extends QuestionEvent {
  final String attribute2;
  
  const Attribute2Changed({@required this.attribute2});
  
  @override
  List<Object> get props => [attribute2];
}
class Attribute3Changed extends QuestionEvent {
  final String attribute3;
  
  const Attribute3Changed({@required this.attribute3});
  
  @override
  List<Object> get props => [attribute3];
}
class Attribute4Changed extends QuestionEvent {
  final String attribute4;
  
  const Attribute4Changed({@required this.attribute4});
  
  @override
  List<Object> get props => [attribute4];
}
class Attribute5Changed extends QuestionEvent {
  final String attribute5;
  
  const Attribute5Changed({@required this.attribute5});
  
  @override
  List<Object> get props => [attribute5];
}
class OptionalChanged extends QuestionEvent {
  final bool optional;
  
  const OptionalChanged({@required this.optional});
  
  @override
  List<Object> get props => [optional];
}
class CreatedDateChanged extends QuestionEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends QuestionEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends QuestionEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends QuestionEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class QuestionFormSubmitted extends QuestionEvent {}

class LoadQuestionByIdForEdit extends QuestionEvent {
  final int id;

  const LoadQuestionByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteQuestionById extends QuestionEvent {
  final int id;

  const DeleteQuestionById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadQuestionByIdForView extends QuestionEvent {
  final int id;

  const LoadQuestionByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
