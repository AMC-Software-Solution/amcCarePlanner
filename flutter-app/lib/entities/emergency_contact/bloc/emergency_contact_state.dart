part of 'emergency_contact_bloc.dart';

enum EmergencyContactStatusUI {init, loading, error, done}
enum EmergencyContactDeleteStatus {ok, ko, none}

class EmergencyContactState extends Equatable {
  final List<EmergencyContact> emergencyContacts;
  final EmergencyContact loadedEmergencyContact;
  final bool editMode;
  final EmergencyContactDeleteStatus deleteStatus;
  final EmergencyContactStatusUI emergencyContactStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final NameInput name;
  final ContactRelationshipInput contactRelationship;
  final IsKeyHolderInput isKeyHolder;
  final InfoSharingConsentGivenInput infoSharingConsentGiven;
  final PreferredContactNumberInput preferredContactNumber;
  final FullAddressInput fullAddress;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  EmergencyContactState(
CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.emergencyContacts = const [],
    this.emergencyContactStatusUI = EmergencyContactStatusUI.init,
    this.loadedEmergencyContact = const EmergencyContact(0,'','',false,false,'','',null,null,0,false,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = EmergencyContactDeleteStatus.none,
    this.name = const NameInput.pure(),
    this.contactRelationship = const ContactRelationshipInput.pure(),
    this.isKeyHolder = const IsKeyHolderInput.pure(),
    this.infoSharingConsentGiven = const InfoSharingConsentGivenInput.pure(),
    this.preferredContactNumber = const PreferredContactNumberInput.pure(),
    this.fullAddress = const FullAddressInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  EmergencyContactState copyWith({
    List<EmergencyContact> emergencyContacts,
    EmergencyContactStatusUI emergencyContactStatusUI,
    bool editMode,
    EmergencyContactDeleteStatus deleteStatus,
    EmergencyContact loadedEmergencyContact,
    FormzStatus formStatus,
    String generalNotificationKey,
    NameInput name,
    ContactRelationshipInput contactRelationship,
    IsKeyHolderInput isKeyHolder,
    InfoSharingConsentGivenInput infoSharingConsentGiven,
    PreferredContactNumberInput preferredContactNumber,
    FullAddressInput fullAddress,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return EmergencyContactState(
        createdDate,
        lastUpdatedDate,
      emergencyContacts: emergencyContacts ?? this.emergencyContacts,
      emergencyContactStatusUI: emergencyContactStatusUI ?? this.emergencyContactStatusUI,
      loadedEmergencyContact: loadedEmergencyContact ?? this.loadedEmergencyContact,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      name: name ?? this.name,
      contactRelationship: contactRelationship ?? this.contactRelationship,
      isKeyHolder: isKeyHolder ?? this.isKeyHolder,
      infoSharingConsentGiven: infoSharingConsentGiven ?? this.infoSharingConsentGiven,
      preferredContactNumber: preferredContactNumber ?? this.preferredContactNumber,
      fullAddress: fullAddress ?? this.fullAddress,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [emergencyContacts, emergencyContactStatusUI,
     loadedEmergencyContact, editMode, deleteStatus, formStatus, generalNotificationKey, 
name,contactRelationship,isKeyHolder,infoSharingConsentGiven,preferredContactNumber,fullAddress,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
