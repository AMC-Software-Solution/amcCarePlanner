part of 'timesheet_bloc.dart';

abstract class TimesheetEvent extends Equatable {
  const TimesheetEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitTimesheetList extends TimesheetEvent {}

class DescriptionChanged extends TimesheetEvent {
  final String description;
  
  const DescriptionChanged({@required this.description});
  
  @override
  List<Object> get props => [description];
}
class TimesheetDateChanged extends TimesheetEvent {
  final DateTime timesheetDate;
  
  const TimesheetDateChanged({@required this.timesheetDate});
  
  @override
  List<Object> get props => [timesheetDate];
}
class StartTimeChanged extends TimesheetEvent {
  final String startTime;
  
  const StartTimeChanged({@required this.startTime});
  
  @override
  List<Object> get props => [startTime];
}
class EndTimeChanged extends TimesheetEvent {
  final String endTime;
  
  const EndTimeChanged({@required this.endTime});
  
  @override
  List<Object> get props => [endTime];
}
class HoursWorkedChanged extends TimesheetEvent {
  final int hoursWorked;
  
  const HoursWorkedChanged({@required this.hoursWorked});
  
  @override
  List<Object> get props => [hoursWorked];
}
class BreakStartTimeChanged extends TimesheetEvent {
  final String breakStartTime;
  
  const BreakStartTimeChanged({@required this.breakStartTime});
  
  @override
  List<Object> get props => [breakStartTime];
}
class BreakEndTimeChanged extends TimesheetEvent {
  final String breakEndTime;
  
  const BreakEndTimeChanged({@required this.breakEndTime});
  
  @override
  List<Object> get props => [breakEndTime];
}
class NoteChanged extends TimesheetEvent {
  final String note;
  
  const NoteChanged({@required this.note});
  
  @override
  List<Object> get props => [note];
}
class CreatedDateChanged extends TimesheetEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends TimesheetEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends TimesheetEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends TimesheetEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class TimesheetFormSubmitted extends TimesheetEvent {}

class LoadTimesheetByIdForEdit extends TimesheetEvent {
  final int id;

  const LoadTimesheetByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteTimesheetById extends TimesheetEvent {
  final int id;

  const DeleteTimesheetById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadTimesheetByIdForView extends TimesheetEvent {
  final int id;

  const LoadTimesheetByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
