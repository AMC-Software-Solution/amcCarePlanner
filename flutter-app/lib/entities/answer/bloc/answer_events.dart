part of 'answer_bloc.dart';

abstract class AnswerEvent extends Equatable {
  const AnswerEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitAnswerList extends AnswerEvent {}

class AnswerChanged extends AnswerEvent {
  final String answer;
  
  const AnswerChanged({@required this.answer});
  
  @override
  List<Object> get props => [answer];
}
class DescriptionChanged extends AnswerEvent {
  final String description;
  
  const DescriptionChanged({@required this.description});
  
  @override
  List<Object> get props => [description];
}
class Attribute1Changed extends AnswerEvent {
  final String attribute1;
  
  const Attribute1Changed({@required this.attribute1});
  
  @override
  List<Object> get props => [attribute1];
}
class Attribute2Changed extends AnswerEvent {
  final String attribute2;
  
  const Attribute2Changed({@required this.attribute2});
  
  @override
  List<Object> get props => [attribute2];
}
class Attribute3Changed extends AnswerEvent {
  final String attribute3;
  
  const Attribute3Changed({@required this.attribute3});
  
  @override
  List<Object> get props => [attribute3];
}
class Attribute4Changed extends AnswerEvent {
  final String attribute4;
  
  const Attribute4Changed({@required this.attribute4});
  
  @override
  List<Object> get props => [attribute4];
}
class Attribute5Changed extends AnswerEvent {
  final String attribute5;
  
  const Attribute5Changed({@required this.attribute5});
  
  @override
  List<Object> get props => [attribute5];
}
class CreatedDateChanged extends AnswerEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends AnswerEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends AnswerEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends AnswerEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class AnswerFormSubmitted extends AnswerEvent {}

class LoadAnswerByIdForEdit extends AnswerEvent {
  final int id;

  const LoadAnswerByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteAnswerById extends AnswerEvent {
  final int id;

  const DeleteAnswerById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadAnswerByIdForView extends AnswerEvent {
  final int id;

  const LoadAnswerByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
