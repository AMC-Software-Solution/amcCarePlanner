part of 'power_of_attorney_bloc.dart';

abstract class PowerOfAttorneyEvent extends Equatable {
  const PowerOfAttorneyEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitPowerOfAttorneyList extends PowerOfAttorneyEvent {}

class PowerOfAttorneyConsentChanged extends PowerOfAttorneyEvent {
  final bool powerOfAttorneyConsent;
  
  const PowerOfAttorneyConsentChanged({@required this.powerOfAttorneyConsent});
  
  @override
  List<Object> get props => [powerOfAttorneyConsent];
}
class HealthAndWelfareChanged extends PowerOfAttorneyEvent {
  final bool healthAndWelfare;
  
  const HealthAndWelfareChanged({@required this.healthAndWelfare});
  
  @override
  List<Object> get props => [healthAndWelfare];
}
class HealthAndWelfareNameChanged extends PowerOfAttorneyEvent {
  final String healthAndWelfareName;
  
  const HealthAndWelfareNameChanged({@required this.healthAndWelfareName});
  
  @override
  List<Object> get props => [healthAndWelfareName];
}
class PropertyAndFinAffairsChanged extends PowerOfAttorneyEvent {
  final bool propertyAndFinAffairs;
  
  const PropertyAndFinAffairsChanged({@required this.propertyAndFinAffairs});
  
  @override
  List<Object> get props => [propertyAndFinAffairs];
}
class PropertyAndFinAffairsNameChanged extends PowerOfAttorneyEvent {
  final String propertyAndFinAffairsName;
  
  const PropertyAndFinAffairsNameChanged({@required this.propertyAndFinAffairsName});
  
  @override
  List<Object> get props => [propertyAndFinAffairsName];
}
class CreatedDateChanged extends PowerOfAttorneyEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends PowerOfAttorneyEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends PowerOfAttorneyEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends PowerOfAttorneyEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class PowerOfAttorneyFormSubmitted extends PowerOfAttorneyEvent {}

class LoadPowerOfAttorneyByIdForEdit extends PowerOfAttorneyEvent {
  final int id;

  const LoadPowerOfAttorneyByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeletePowerOfAttorneyById extends PowerOfAttorneyEvent {
  final int id;

  const DeletePowerOfAttorneyById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadPowerOfAttorneyByIdForView extends PowerOfAttorneyEvent {
  final int id;

  const LoadPowerOfAttorneyByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
