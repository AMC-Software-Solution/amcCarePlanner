part of 'employee_holiday_bloc.dart';

enum EmployeeHolidayStatusUI {init, loading, error, done}
enum EmployeeHolidayDeleteStatus {ok, ko, none}

class EmployeeHolidayState extends Equatable {
  final List<EmployeeHoliday> employeeHolidays;
  final EmployeeHoliday loadedEmployeeHoliday;
  final bool editMode;
  final EmployeeHolidayDeleteStatus deleteStatus;
  final EmployeeHolidayStatusUI employeeHolidayStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final DescriptionInput description;
  final StartDateInput startDate;
  final EndDateInput endDate;
  final EmployeeHolidayTypeInput employeeHolidayType;
  final ApprovedDateInput approvedDate;
  final RequestedDateInput requestedDate;
  final HolidayStatusInput holidayStatus;
  final NoteInput note;
  final RejectionReasonInput rejectionReason;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  EmployeeHolidayState(
StartDateInput startDate,EndDateInput endDate,ApprovedDateInput approvedDate,RequestedDateInput requestedDate,CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.employeeHolidays = const [],
    this.employeeHolidayStatusUI = EmployeeHolidayStatusUI.init,
    this.loadedEmployeeHoliday = const EmployeeHoliday(0,'',null,null,null,null,null,null,'','',null,null,0,false,null,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = EmployeeHolidayDeleteStatus.none,
    this.description = const DescriptionInput.pure(),
    this.employeeHolidayType = const EmployeeHolidayTypeInput.pure(),
    this.holidayStatus = const HolidayStatusInput.pure(),
    this.note = const NoteInput.pure(),
    this.rejectionReason = const RejectionReasonInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.startDate = startDate ?? StartDateInput.pure(),
this.endDate = endDate ?? EndDateInput.pure(),
this.approvedDate = approvedDate ?? ApprovedDateInput.pure(),
this.requestedDate = requestedDate ?? RequestedDateInput.pure(),
this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  EmployeeHolidayState copyWith({
    List<EmployeeHoliday> employeeHolidays,
    EmployeeHolidayStatusUI employeeHolidayStatusUI,
    bool editMode,
    EmployeeHolidayDeleteStatus deleteStatus,
    EmployeeHoliday loadedEmployeeHoliday,
    FormzStatus formStatus,
    String generalNotificationKey,
    DescriptionInput description,
    StartDateInput startDate,
    EndDateInput endDate,
    EmployeeHolidayTypeInput employeeHolidayType,
    ApprovedDateInput approvedDate,
    RequestedDateInput requestedDate,
    HolidayStatusInput holidayStatus,
    NoteInput note,
    RejectionReasonInput rejectionReason,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return EmployeeHolidayState(
        startDate,
        endDate,
        approvedDate,
        requestedDate,
        createdDate,
        lastUpdatedDate,
      employeeHolidays: employeeHolidays ?? this.employeeHolidays,
      employeeHolidayStatusUI: employeeHolidayStatusUI ?? this.employeeHolidayStatusUI,
      loadedEmployeeHoliday: loadedEmployeeHoliday ?? this.loadedEmployeeHoliday,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      description: description ?? this.description,
      employeeHolidayType: employeeHolidayType ?? this.employeeHolidayType,
      holidayStatus: holidayStatus ?? this.holidayStatus,
      note: note ?? this.note,
      rejectionReason: rejectionReason ?? this.rejectionReason,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [employeeHolidays, employeeHolidayStatusUI,
     loadedEmployeeHoliday, editMode, deleteStatus, formStatus, generalNotificationKey, 
description,startDate,endDate,employeeHolidayType,approvedDate,requestedDate,holidayStatus,note,rejectionReason,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
