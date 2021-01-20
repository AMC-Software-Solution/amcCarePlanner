part of 'consent_bloc.dart';

abstract class ConsentEvent extends Equatable {
  const ConsentEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitConsentList extends ConsentEvent {}

class TitleChanged extends ConsentEvent {
  final String title;
  
  const TitleChanged({@required this.title});
  
  @override
  List<Object> get props => [title];
}
class DescriptionChanged extends ConsentEvent {
  final String description;
  
  const DescriptionChanged({@required this.description});
  
  @override
  List<Object> get props => [description];
}
class GiveConsentChanged extends ConsentEvent {
  final bool giveConsent;
  
  const GiveConsentChanged({@required this.giveConsent});
  
  @override
  List<Object> get props => [giveConsent];
}
class ArrangementsChanged extends ConsentEvent {
  final String arrangements;
  
  const ArrangementsChanged({@required this.arrangements});
  
  @override
  List<Object> get props => [arrangements];
}
class ServiceUserSignatureChanged extends ConsentEvent {
  final String serviceUserSignature;
  
  const ServiceUserSignatureChanged({@required this.serviceUserSignature});
  
  @override
  List<Object> get props => [serviceUserSignature];
}
class SignatureImageUrlChanged extends ConsentEvent {
  final String signatureImageUrl;
  
  const SignatureImageUrlChanged({@required this.signatureImageUrl});
  
  @override
  List<Object> get props => [signatureImageUrl];
}
class ConsentDateChanged extends ConsentEvent {
  final DateTime consentDate;
  
  const ConsentDateChanged({@required this.consentDate});
  
  @override
  List<Object> get props => [consentDate];
}
class CreatedDateChanged extends ConsentEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends ConsentEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends ConsentEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends ConsentEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class ConsentFormSubmitted extends ConsentEvent {}

class LoadConsentByIdForEdit extends ConsentEvent {
  final int id;

  const LoadConsentByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteConsentById extends ConsentEvent {
  final int id;

  const DeleteConsentById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadConsentByIdForView extends ConsentEvent {
  final int id;

  const LoadConsentByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
