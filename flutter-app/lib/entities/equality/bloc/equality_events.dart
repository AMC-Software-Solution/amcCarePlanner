part of 'equality_bloc.dart';

abstract class EqualityEvent extends Equatable {
  const EqualityEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitEqualityList extends EqualityEvent {}

class GenderChanged extends EqualityEvent {
  final ServiceUserGender gender;
  
  const GenderChanged({@required this.gender});
  
  @override
  List<Object> get props => [gender];
}
class MaritalStatusChanged extends EqualityEvent {
  final MaritalStatus maritalStatus;
  
  const MaritalStatusChanged({@required this.maritalStatus});
  
  @override
  List<Object> get props => [maritalStatus];
}
class ReligionChanged extends EqualityEvent {
  final Religion religion;
  
  const ReligionChanged({@required this.religion});
  
  @override
  List<Object> get props => [religion];
}
class CreatedDateChanged extends EqualityEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends EqualityEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends EqualityEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends EqualityEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class EqualityFormSubmitted extends EqualityEvent {}

class LoadEqualityByIdForEdit extends EqualityEvent {
  final int id;

  const LoadEqualityByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteEqualityById extends EqualityEvent {
  final int id;

  const DeleteEqualityById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadEqualityByIdForView extends EqualityEvent {
  final int id;

  const LoadEqualityByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
