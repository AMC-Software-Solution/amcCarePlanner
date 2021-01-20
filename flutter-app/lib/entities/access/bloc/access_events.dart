part of 'access_bloc.dart';

abstract class AccessEvent extends Equatable {
  const AccessEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitAccessList extends AccessEvent {}

class KeySafeNumberChanged extends AccessEvent {
  final String keySafeNumber;
  
  const KeySafeNumberChanged({@required this.keySafeNumber});
  
  @override
  List<Object> get props => [keySafeNumber];
}
class AccessDetailsChanged extends AccessEvent {
  final String accessDetails;
  
  const AccessDetailsChanged({@required this.accessDetails});
  
  @override
  List<Object> get props => [accessDetails];
}
class CreatedDateChanged extends AccessEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends AccessEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends AccessEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends AccessEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class AccessFormSubmitted extends AccessEvent {}

class LoadAccessByIdForEdit extends AccessEvent {
  final int id;

  const LoadAccessByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteAccessById extends AccessEvent {
  final int id;

  const DeleteAccessById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadAccessByIdForView extends AccessEvent {
  final int id;

  const LoadAccessByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
