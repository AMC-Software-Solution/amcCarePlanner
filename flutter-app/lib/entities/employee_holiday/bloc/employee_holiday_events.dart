part of 'employee_holiday_bloc.dart';

abstract class EmployeeHolidayEvent extends Equatable {
  const EmployeeHolidayEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitEmployeeHolidayList extends EmployeeHolidayEvent {}

class DescriptionChanged extends EmployeeHolidayEvent {
  final String description;
  
  const DescriptionChanged({@required this.description});
  
  @override
  List<Object> get props => [description];
}
class StartDateChanged extends EmployeeHolidayEvent {
  final DateTime startDate;
  
  const StartDateChanged({@required this.startDate});
  
  @override
  List<Object> get props => [startDate];
}
class EndDateChanged extends EmployeeHolidayEvent {
  final DateTime endDate;
  
  const EndDateChanged({@required this.endDate});
  
  @override
  List<Object> get props => [endDate];
}
class EmployeeHolidayTypeChanged extends EmployeeHolidayEvent {
  final EmployeeHolidayType employeeHolidayType;
  
  const EmployeeHolidayTypeChanged({@required this.employeeHolidayType});
  
  @override
  List<Object> get props => [employeeHolidayType];
}
class ApprovedDateChanged extends EmployeeHolidayEvent {
  final DateTime approvedDate;
  
  const ApprovedDateChanged({@required this.approvedDate});
  
  @override
  List<Object> get props => [approvedDate];
}
class RequestedDateChanged extends EmployeeHolidayEvent {
  final DateTime requestedDate;
  
  const RequestedDateChanged({@required this.requestedDate});
  
  @override
  List<Object> get props => [requestedDate];
}
class HolidayStatusChanged extends EmployeeHolidayEvent {
  final HolidayStatus holidayStatus;
  
  const HolidayStatusChanged({@required this.holidayStatus});
  
  @override
  List<Object> get props => [holidayStatus];
}
class NoteChanged extends EmployeeHolidayEvent {
  final String note;
  
  const NoteChanged({@required this.note});
  
  @override
  List<Object> get props => [note];
}
class RejectionReasonChanged extends EmployeeHolidayEvent {
  final String rejectionReason;
  
  const RejectionReasonChanged({@required this.rejectionReason});
  
  @override
  List<Object> get props => [rejectionReason];
}
class CreatedDateChanged extends EmployeeHolidayEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends EmployeeHolidayEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends EmployeeHolidayEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends EmployeeHolidayEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class EmployeeHolidayFormSubmitted extends EmployeeHolidayEvent {}

class LoadEmployeeHolidayByIdForEdit extends EmployeeHolidayEvent {
  final int id;

  const LoadEmployeeHolidayByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteEmployeeHolidayById extends EmployeeHolidayEvent {
  final int id;

  const DeleteEmployeeHolidayById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadEmployeeHolidayByIdForView extends EmployeeHolidayEvent {
  final int id;

  const LoadEmployeeHolidayByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
