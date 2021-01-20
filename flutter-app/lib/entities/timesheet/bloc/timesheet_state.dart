part of 'timesheet_bloc.dart';

enum TimesheetStatusUI {init, loading, error, done}
enum TimesheetDeleteStatus {ok, ko, none}

class TimesheetState extends Equatable {
  final List<Timesheet> timesheets;
  final Timesheet loadedTimesheet;
  final bool editMode;
  final TimesheetDeleteStatus deleteStatus;
  final TimesheetStatusUI timesheetStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final DescriptionInput description;
  final TimesheetDateInput timesheetDate;
  final StartTimeInput startTime;
  final EndTimeInput endTime;
  final HoursWorkedInput hoursWorked;
  final BreakStartTimeInput breakStartTime;
  final BreakEndTimeInput breakEndTime;
  final NoteInput note;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  TimesheetState(
TimesheetDateInput timesheetDate,CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.timesheets = const [],
    this.timesheetStatusUI = TimesheetStatusUI.init,
    this.loadedTimesheet = const Timesheet(0,'',null,'','',0,'','','',null,null,0,false,null,null,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = TimesheetDeleteStatus.none,
    this.description = const DescriptionInput.pure(),
    this.startTime = const StartTimeInput.pure(),
    this.endTime = const EndTimeInput.pure(),
    this.hoursWorked = const HoursWorkedInput.pure(),
    this.breakStartTime = const BreakStartTimeInput.pure(),
    this.breakEndTime = const BreakEndTimeInput.pure(),
    this.note = const NoteInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.timesheetDate = timesheetDate ?? TimesheetDateInput.pure(),
this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  TimesheetState copyWith({
    List<Timesheet> timesheets,
    TimesheetStatusUI timesheetStatusUI,
    bool editMode,
    TimesheetDeleteStatus deleteStatus,
    Timesheet loadedTimesheet,
    FormzStatus formStatus,
    String generalNotificationKey,
    DescriptionInput description,
    TimesheetDateInput timesheetDate,
    StartTimeInput startTime,
    EndTimeInput endTime,
    HoursWorkedInput hoursWorked,
    BreakStartTimeInput breakStartTime,
    BreakEndTimeInput breakEndTime,
    NoteInput note,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return TimesheetState(
        timesheetDate,
        createdDate,
        lastUpdatedDate,
      timesheets: timesheets ?? this.timesheets,
      timesheetStatusUI: timesheetStatusUI ?? this.timesheetStatusUI,
      loadedTimesheet: loadedTimesheet ?? this.loadedTimesheet,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      description: description ?? this.description,
      startTime: startTime ?? this.startTime,
      endTime: endTime ?? this.endTime,
      hoursWorked: hoursWorked ?? this.hoursWorked,
      breakStartTime: breakStartTime ?? this.breakStartTime,
      breakEndTime: breakEndTime ?? this.breakEndTime,
      note: note ?? this.note,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [timesheets, timesheetStatusUI,
     loadedTimesheet, editMode, deleteStatus, formStatus, generalNotificationKey, 
description,timesheetDate,startTime,endTime,hoursWorked,breakStartTime,breakEndTime,note,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
