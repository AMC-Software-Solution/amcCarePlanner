part of 'power_of_attorney_bloc.dart';

enum PowerOfAttorneyStatusUI {init, loading, error, done}
enum PowerOfAttorneyDeleteStatus {ok, ko, none}

class PowerOfAttorneyState extends Equatable {
  final List<PowerOfAttorney> powerOfAttorneys;
  final PowerOfAttorney loadedPowerOfAttorney;
  final bool editMode;
  final PowerOfAttorneyDeleteStatus deleteStatus;
  final PowerOfAttorneyStatusUI powerOfAttorneyStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final PowerOfAttorneyConsentInput powerOfAttorneyConsent;
  final HealthAndWelfareInput healthAndWelfare;
  final HealthAndWelfareNameInput healthAndWelfareName;
  final PropertyAndFinAffairsInput propertyAndFinAffairs;
  final PropertyAndFinAffairsNameInput propertyAndFinAffairsName;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  PowerOfAttorneyState(
CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.powerOfAttorneys = const [],
    this.powerOfAttorneyStatusUI = PowerOfAttorneyStatusUI.init,
    this.loadedPowerOfAttorney = const PowerOfAttorney(0,false,false,'',false,'',null,null,0,false,null,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = PowerOfAttorneyDeleteStatus.none,
    this.powerOfAttorneyConsent = const PowerOfAttorneyConsentInput.pure(),
    this.healthAndWelfare = const HealthAndWelfareInput.pure(),
    this.healthAndWelfareName = const HealthAndWelfareNameInput.pure(),
    this.propertyAndFinAffairs = const PropertyAndFinAffairsInput.pure(),
    this.propertyAndFinAffairsName = const PropertyAndFinAffairsNameInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  PowerOfAttorneyState copyWith({
    List<PowerOfAttorney> powerOfAttorneys,
    PowerOfAttorneyStatusUI powerOfAttorneyStatusUI,
    bool editMode,
    PowerOfAttorneyDeleteStatus deleteStatus,
    PowerOfAttorney loadedPowerOfAttorney,
    FormzStatus formStatus,
    String generalNotificationKey,
    PowerOfAttorneyConsentInput powerOfAttorneyConsent,
    HealthAndWelfareInput healthAndWelfare,
    HealthAndWelfareNameInput healthAndWelfareName,
    PropertyAndFinAffairsInput propertyAndFinAffairs,
    PropertyAndFinAffairsNameInput propertyAndFinAffairsName,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return PowerOfAttorneyState(
        createdDate,
        lastUpdatedDate,
      powerOfAttorneys: powerOfAttorneys ?? this.powerOfAttorneys,
      powerOfAttorneyStatusUI: powerOfAttorneyStatusUI ?? this.powerOfAttorneyStatusUI,
      loadedPowerOfAttorney: loadedPowerOfAttorney ?? this.loadedPowerOfAttorney,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      powerOfAttorneyConsent: powerOfAttorneyConsent ?? this.powerOfAttorneyConsent,
      healthAndWelfare: healthAndWelfare ?? this.healthAndWelfare,
      healthAndWelfareName: healthAndWelfareName ?? this.healthAndWelfareName,
      propertyAndFinAffairs: propertyAndFinAffairs ?? this.propertyAndFinAffairs,
      propertyAndFinAffairsName: propertyAndFinAffairsName ?? this.propertyAndFinAffairsName,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [powerOfAttorneys, powerOfAttorneyStatusUI,
     loadedPowerOfAttorney, editMode, deleteStatus, formStatus, generalNotificationKey, 
powerOfAttorneyConsent,healthAndWelfare,healthAndWelfareName,propertyAndFinAffairs,propertyAndFinAffairsName,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
