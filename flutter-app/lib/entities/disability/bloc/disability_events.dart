part of 'disability_bloc.dart';

abstract class DisabilityEvent extends Equatable {
  const DisabilityEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitDisabilityList extends DisabilityEvent {}

class IsDisabledChanged extends DisabilityEvent {
  final bool isDisabled;
  
  const IsDisabledChanged({@required this.isDisabled});
  
  @override
  List<Object> get props => [isDisabled];
}
class NoteChanged extends DisabilityEvent {
  final String note;
  
  const NoteChanged({@required this.note});
  
  @override
  List<Object> get props => [note];
}
class CreatedDateChanged extends DisabilityEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends DisabilityEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends DisabilityEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends DisabilityEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class DisabilityFormSubmitted extends DisabilityEvent {}

class LoadDisabilityByIdForEdit extends DisabilityEvent {
  final int id;

  const LoadDisabilityByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteDisabilityById extends DisabilityEvent {
  final int id;

  const DeleteDisabilityById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadDisabilityByIdForView extends DisabilityEvent {
  final int id;

  const LoadDisabilityByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
