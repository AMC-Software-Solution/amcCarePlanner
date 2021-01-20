part of 'consent_bloc.dart';

enum ConsentStatusUI {init, loading, error, done}
enum ConsentDeleteStatus {ok, ko, none}

class ConsentState extends Equatable {
  final List<Consent> consents;
  final Consent loadedConsent;
  final bool editMode;
  final ConsentDeleteStatus deleteStatus;
  final ConsentStatusUI consentStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final TitleInput title;
  final DescriptionInput description;
  final GiveConsentInput giveConsent;
  final ArrangementsInput arrangements;
  final ServiceUserSignatureInput serviceUserSignature;
  final SignatureImageUrlInput signatureImageUrl;
  final ConsentDateInput consentDate;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  ConsentState(
ConsentDateInput consentDate,CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.consents = const [],
    this.consentStatusUI = ConsentStatusUI.init,
    this.loadedConsent = const Consent(0,'','',false,'','','',null,null,null,0,false,null,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = ConsentDeleteStatus.none,
    this.title = const TitleInput.pure(),
    this.description = const DescriptionInput.pure(),
    this.giveConsent = const GiveConsentInput.pure(),
    this.arrangements = const ArrangementsInput.pure(),
    this.serviceUserSignature = const ServiceUserSignatureInput.pure(),
    this.signatureImageUrl = const SignatureImageUrlInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.consentDate = consentDate ?? ConsentDateInput.pure(),
this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  ConsentState copyWith({
    List<Consent> consents,
    ConsentStatusUI consentStatusUI,
    bool editMode,
    ConsentDeleteStatus deleteStatus,
    Consent loadedConsent,
    FormzStatus formStatus,
    String generalNotificationKey,
    TitleInput title,
    DescriptionInput description,
    GiveConsentInput giveConsent,
    ArrangementsInput arrangements,
    ServiceUserSignatureInput serviceUserSignature,
    SignatureImageUrlInput signatureImageUrl,
    ConsentDateInput consentDate,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return ConsentState(
        consentDate,
        createdDate,
        lastUpdatedDate,
      consents: consents ?? this.consents,
      consentStatusUI: consentStatusUI ?? this.consentStatusUI,
      loadedConsent: loadedConsent ?? this.loadedConsent,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      title: title ?? this.title,
      description: description ?? this.description,
      giveConsent: giveConsent ?? this.giveConsent,
      arrangements: arrangements ?? this.arrangements,
      serviceUserSignature: serviceUserSignature ?? this.serviceUserSignature,
      signatureImageUrl: signatureImageUrl ?? this.signatureImageUrl,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [consents, consentStatusUI,
     loadedConsent, editMode, deleteStatus, formStatus, generalNotificationKey, 
title,description,giveConsent,arrangements,serviceUserSignature,signatureImageUrl,consentDate,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
