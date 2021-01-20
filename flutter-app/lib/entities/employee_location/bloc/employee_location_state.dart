part of 'employee_location_bloc.dart';

enum EmployeeLocationStatusUI {init, loading, error, done}
enum EmployeeLocationDeleteStatus {ok, ko, none}

class EmployeeLocationState extends Equatable {
  final List<EmployeeLocation> employeeLocations;
  final EmployeeLocation loadedEmployeeLocation;
  final bool editMode;
  final EmployeeLocationDeleteStatus deleteStatus;
  final EmployeeLocationStatusUI employeeLocationStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final LatitudeInput latitude;
  final LongitudeInput longitude;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  EmployeeLocationState(
CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.employeeLocations = const [],
    this.employeeLocationStatusUI = EmployeeLocationStatusUI.init,
    this.loadedEmployeeLocation = const EmployeeLocation(0,'','',null,null,0,false,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = EmployeeLocationDeleteStatus.none,
    this.latitude = const LatitudeInput.pure(),
    this.longitude = const LongitudeInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  EmployeeLocationState copyWith({
    List<EmployeeLocation> employeeLocations,
    EmployeeLocationStatusUI employeeLocationStatusUI,
    bool editMode,
    EmployeeLocationDeleteStatus deleteStatus,
    EmployeeLocation loadedEmployeeLocation,
    FormzStatus formStatus,
    String generalNotificationKey,
    LatitudeInput latitude,
    LongitudeInput longitude,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return EmployeeLocationState(
        createdDate,
        lastUpdatedDate,
      employeeLocations: employeeLocations ?? this.employeeLocations,
      employeeLocationStatusUI: employeeLocationStatusUI ?? this.employeeLocationStatusUI,
      loadedEmployeeLocation: loadedEmployeeLocation ?? this.loadedEmployeeLocation,
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
  List<Object> get props => [employeeLocations, employeeLocationStatusUI,
     loadedEmployeeLocation, editMode, deleteStatus, formStatus, generalNotificationKey, 
latitude,longitude,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
