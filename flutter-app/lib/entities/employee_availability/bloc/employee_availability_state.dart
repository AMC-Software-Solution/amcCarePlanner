part of 'employee_availability_bloc.dart';

enum EmployeeAvailabilityStatusUI {init, loading, error, done}
enum EmployeeAvailabilityDeleteStatus {ok, ko, none}

class EmployeeAvailabilityState extends Equatable {
  final List<EmployeeAvailability> employeeAvailabilities;
  final EmployeeAvailability loadedEmployeeAvailability;
  final bool editMode;
  final EmployeeAvailabilityDeleteStatus deleteStatus;
  final EmployeeAvailabilityStatusUI employeeAvailabilityStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final AvailableForWorkInput availableForWork;
  final AvailableMondayInput availableMonday;
  final AvailableTuesdayInput availableTuesday;
  final AvailableWednesdayInput availableWednesday;
  final AvailableThursdayInput availableThursday;
  final AvailableFridayInput availableFriday;
  final AvailableSaturdayInput availableSaturday;
  final AvailableSundayInput availableSunday;
  final PreferredShiftInput preferredShift;
  final HasExtraDataInput hasExtraData;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;

  
  EmployeeAvailabilityState(
LastUpdatedDateInput lastUpdatedDate,{
    this.employeeAvailabilities = const [],
    this.employeeAvailabilityStatusUI = EmployeeAvailabilityStatusUI.init,
    this.loadedEmployeeAvailability = const EmployeeAvailability(0,false,false,false,false,false,false,false,false,null,false,null,0,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = EmployeeAvailabilityDeleteStatus.none,
    this.availableForWork = const AvailableForWorkInput.pure(),
    this.availableMonday = const AvailableMondayInput.pure(),
    this.availableTuesday = const AvailableTuesdayInput.pure(),
    this.availableWednesday = const AvailableWednesdayInput.pure(),
    this.availableThursday = const AvailableThursdayInput.pure(),
    this.availableFriday = const AvailableFridayInput.pure(),
    this.availableSaturday = const AvailableSaturdayInput.pure(),
    this.availableSunday = const AvailableSundayInput.pure(),
    this.preferredShift = const PreferredShiftInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
    this.clientId = const ClientIdInput.pure(),
  }):this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  EmployeeAvailabilityState copyWith({
    List<EmployeeAvailability> employeeAvailabilities,
    EmployeeAvailabilityStatusUI employeeAvailabilityStatusUI,
    bool editMode,
    EmployeeAvailabilityDeleteStatus deleteStatus,
    EmployeeAvailability loadedEmployeeAvailability,
    FormzStatus formStatus,
    String generalNotificationKey,
    AvailableForWorkInput availableForWork,
    AvailableMondayInput availableMonday,
    AvailableTuesdayInput availableTuesday,
    AvailableWednesdayInput availableWednesday,
    AvailableThursdayInput availableThursday,
    AvailableFridayInput availableFriday,
    AvailableSaturdayInput availableSaturday,
    AvailableSundayInput availableSunday,
    PreferredShiftInput preferredShift,
    HasExtraDataInput hasExtraData,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
  }) {
    return EmployeeAvailabilityState(
        lastUpdatedDate,
      employeeAvailabilities: employeeAvailabilities ?? this.employeeAvailabilities,
      employeeAvailabilityStatusUI: employeeAvailabilityStatusUI ?? this.employeeAvailabilityStatusUI,
      loadedEmployeeAvailability: loadedEmployeeAvailability ?? this.loadedEmployeeAvailability,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      availableForWork: availableForWork ?? this.availableForWork,
      availableMonday: availableMonday ?? this.availableMonday,
      availableTuesday: availableTuesday ?? this.availableTuesday,
      availableWednesday: availableWednesday ?? this.availableWednesday,
      availableThursday: availableThursday ?? this.availableThursday,
      availableFriday: availableFriday ?? this.availableFriday,
      availableSaturday: availableSaturday ?? this.availableSaturday,
      availableSunday: availableSunday ?? this.availableSunday,
      preferredShift: preferredShift ?? this.preferredShift,
      hasExtraData: hasExtraData ?? this.hasExtraData,
      clientId: clientId ?? this.clientId,
    );
  }

  @override
  List<Object> get props => [employeeAvailabilities, employeeAvailabilityStatusUI,
     loadedEmployeeAvailability, editMode, deleteStatus, formStatus, generalNotificationKey, 
availableForWork,availableMonday,availableTuesday,availableWednesday,availableThursday,availableFriday,availableSaturday,availableSunday,preferredShift,hasExtraData,lastUpdatedDate,clientId,];

  @override
  bool get stringify => true;
}
