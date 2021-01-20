part of 'eligibility_bloc.dart';

abstract class EligibilityEvent extends Equatable {
  const EligibilityEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitEligibilityList extends EligibilityEvent {}

class IsEligibleChanged extends EligibilityEvent {
  final bool isEligible;
  
  const IsEligibleChanged({@required this.isEligible});
  
  @override
  List<Object> get props => [isEligible];
}
class NoteChanged extends EligibilityEvent {
  final String note;
  
  const NoteChanged({@required this.note});
  
  @override
  List<Object> get props => [note];
}
class CreatedDateChanged extends EligibilityEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends EligibilityEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends EligibilityEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends EligibilityEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class EligibilityFormSubmitted extends EligibilityEvent {}

class LoadEligibilityByIdForEdit extends EligibilityEvent {
  final int id;

  const LoadEligibilityByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteEligibilityById extends EligibilityEvent {
  final int id;

  const DeleteEligibilityById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadEligibilityByIdForView extends EligibilityEvent {
  final int id;

  const LoadEligibilityByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
