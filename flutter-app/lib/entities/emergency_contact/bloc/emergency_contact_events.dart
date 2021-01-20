part of 'emergency_contact_bloc.dart';

abstract class EmergencyContactEvent extends Equatable {
  const EmergencyContactEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitEmergencyContactList extends EmergencyContactEvent {}

class NameChanged extends EmergencyContactEvent {
  final String name;
  
  const NameChanged({@required this.name});
  
  @override
  List<Object> get props => [name];
}
class ContactRelationshipChanged extends EmergencyContactEvent {
  final String contactRelationship;
  
  const ContactRelationshipChanged({@required this.contactRelationship});
  
  @override
  List<Object> get props => [contactRelationship];
}
class IsKeyHolderChanged extends EmergencyContactEvent {
  final bool isKeyHolder;
  
  const IsKeyHolderChanged({@required this.isKeyHolder});
  
  @override
  List<Object> get props => [isKeyHolder];
}
class InfoSharingConsentGivenChanged extends EmergencyContactEvent {
  final bool infoSharingConsentGiven;
  
  const InfoSharingConsentGivenChanged({@required this.infoSharingConsentGiven});
  
  @override
  List<Object> get props => [infoSharingConsentGiven];
}
class PreferredContactNumberChanged extends EmergencyContactEvent {
  final String preferredContactNumber;
  
  const PreferredContactNumberChanged({@required this.preferredContactNumber});
  
  @override
  List<Object> get props => [preferredContactNumber];
}
class FullAddressChanged extends EmergencyContactEvent {
  final String fullAddress;
  
  const FullAddressChanged({@required this.fullAddress});
  
  @override
  List<Object> get props => [fullAddress];
}
class CreatedDateChanged extends EmergencyContactEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends EmergencyContactEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends EmergencyContactEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends EmergencyContactEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class EmergencyContactFormSubmitted extends EmergencyContactEvent {}

class LoadEmergencyContactByIdForEdit extends EmergencyContactEvent {
  final int id;

  const LoadEmergencyContactByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteEmergencyContactById extends EmergencyContactEvent {
  final int id;

  const DeleteEmergencyContactById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadEmergencyContactByIdForView extends EmergencyContactEvent {
  final int id;

  const LoadEmergencyContactByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
