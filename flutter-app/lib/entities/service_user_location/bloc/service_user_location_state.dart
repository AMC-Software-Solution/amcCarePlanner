part of 'service_user_location_bloc.dart';

enum ServiceUserLocationStatusUI {init, loading, error, done}
enum ServiceUserLocationDeleteStatus {ok, ko, none}

class ServiceUserLocationState extends Equatable {
  final List<ServiceUserLocation> serviceUserLocations;
  final ServiceUserLocation loadedServiceUserLocation;
  final bool editMode;
  final ServiceUserLocationDeleteStatus deleteStatus;
  final ServiceUserLocationStatusUI serviceUserLocationStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final LatitudeInput latitude;
  final LongitudeInput longitude;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  ServiceUserLocationState(
CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.serviceUserLocations = const [],
    this.serviceUserLocationStatusUI = ServiceUserLocationStatusUI.init,
    this.loadedServiceUserLocation = const ServiceUserLocation(0,'','',null,null,0,false,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = ServiceUserLocationDeleteStatus.none,
    this.latitude = const LatitudeInput.pure(),
    this.longitude = const LongitudeInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  ServiceUserLocationState copyWith({
    List<ServiceUserLocation> serviceUserLocations,
    ServiceUserLocationStatusUI serviceUserLocationStatusUI,
    bool editMode,
    ServiceUserLocationDeleteStatus deleteStatus,
    ServiceUserLocation loadedServiceUserLocation,
    FormzStatus formStatus,
    String generalNotificationKey,
    LatitudeInput latitude,
    LongitudeInput longitude,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return ServiceUserLocationState(
        createdDate,
        lastUpdatedDate,
      serviceUserLocations: serviceUserLocations ?? this.serviceUserLocations,
      serviceUserLocationStatusUI: serviceUserLocationStatusUI ?? this.serviceUserLocationStatusUI,
      loadedServiceUserLocation: loadedServiceUserLocation ?? this.loadedServiceUserLocation,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      latitude: latitude ?? this.latitude,
      longitude: longitude ?? this.longitude,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [serviceUserLocations, serviceUserLocationStatusUI,
     loadedServiceUserLocation, editMode, deleteStatus, formStatus, generalNotificationKey, 
latitude,longitude,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
