part of 'service_user_contact_bloc.dart';

enum ServiceUserContactStatusUI {init, loading, error, done}
enum ServiceUserContactDeleteStatus {ok, ko, none}

class ServiceUserContactState extends Equatable {
  final List<ServiceUserContact> serviceUserContacts;
  final ServiceUserContact loadedServiceUserContact;
  final bool editMode;
  final ServiceUserContactDeleteStatus deleteStatus;
  final ServiceUserContactStatusUI serviceUserContactStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final AddressInput address;
  final CityOrTownInput cityOrTown;
  final CountyInput county;
  final PostCodeInput postCode;
  final TelephoneInput telephone;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  ServiceUserContactState(
CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.serviceUserContacts = const [],
    this.serviceUserContactStatusUI = ServiceUserContactStatusUI.init,
    this.loadedServiceUserContact = const ServiceUserContact(0,'','','','','',null,null,0,false,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = ServiceUserContactDeleteStatus.none,
    this.address = const AddressInput.pure(),
    this.cityOrTown = const CityOrTownInput.pure(),
    this.county = const CountyInput.pure(),
    this.postCode = const PostCodeInput.pure(),
    this.telephone = const TelephoneInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  ServiceUserContactState copyWith({
    List<ServiceUserContact> serviceUserContacts,
    ServiceUserContactStatusUI serviceUserContactStatusUI,
    bool editMode,
    ServiceUserContactDeleteStatus deleteStatus,
    ServiceUserContact loadedServiceUserContact,
    FormzStatus formStatus,
    String generalNotificationKey,
    AddressInput address,
    CityOrTownInput cityOrTown,
    CountyInput county,
    PostCodeInput postCode,
    TelephoneInput telephone,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return ServiceUserContactState(
        createdDate,
        lastUpdatedDate,
      serviceUserContacts: serviceUserContacts ?? this.serviceUserContacts,
      serviceUserContactStatusUI: serviceUserContactStatusUI ?? this.serviceUserContactStatusUI,
      loadedServiceUserContact: loadedServiceUserContact ?? this.loadedServiceUserContact,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      address: address ?? this.address,
      cityOrTown: cityOrTown ?? this.cityOrTown,
      county: county ?? this.county,
      postCode: postCode ?? this.postCode,
      telephone: telephone ?? this.telephone,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [serviceUserContacts, serviceUserContactStatusUI,
     loadedServiceUserContact, editMode, deleteStatus, formStatus, generalNotificationKey, 
address,cityOrTown,county,postCode,telephone,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
