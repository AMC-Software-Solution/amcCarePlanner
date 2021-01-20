part of 'carer_service_user_relation_bloc.dart';

abstract class CarerServiceUserRelationEvent extends Equatable {
  const CarerServiceUserRelationEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitCarerServiceUserRelationList extends CarerServiceUserRelationEvent {}

class ReasonChanged extends CarerServiceUserRelationEvent {
  final String reason;
  
  const ReasonChanged({@required this.reason});
  
  @override
  List<Object> get props => [reason];
}
class CountChanged extends CarerServiceUserRelationEvent {
  final int count;
  
  const CountChanged({@required this.count});
  
  @override
  List<Object> get props => [count];
}
class CreatedDateChanged extends CarerServiceUserRelationEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends CarerServiceUserRelationEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends CarerServiceUserRelationEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends CarerServiceUserRelationEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class CarerServiceUserRelationFormSubmitted extends CarerServiceUserRelationEvent {}

class LoadCarerServiceUserRelationByIdForEdit extends CarerServiceUserRelationEvent {
  final int id;

  const LoadCarerServiceUserRelationByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteCarerServiceUserRelationById extends CarerServiceUserRelationEvent {
  final int id;

  const DeleteCarerServiceUserRelationById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadCarerServiceUserRelationByIdForView extends CarerServiceUserRelationEvent {
  final int id;

  const LoadCarerServiceUserRelationByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
