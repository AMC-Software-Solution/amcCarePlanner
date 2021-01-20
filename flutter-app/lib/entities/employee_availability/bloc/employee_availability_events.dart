part of 'employee_availability_bloc.dart';

abstract class EmployeeAvailabilityEvent extends Equatable {
  const EmployeeAvailabilityEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitEmployeeAvailabilityList extends EmployeeAvailabilityEvent {}

class AvailableForWorkChanged extends EmployeeAvailabilityEvent {
  final bool availableForWork;
  
  const AvailableForWorkChanged({@required this.availableForWork});
  
  @override
  List<Object> get props => [availableForWork];
}
class AvailableMondayChanged extends EmployeeAvailabilityEvent {
  final bool availableMonday;
  
  const AvailableMondayChanged({@required this.availableMonday});
  
  @override
  List<Object> get props => [availableMonday];
}
class AvailableTuesdayChanged extends EmployeeAvailabilityEvent {
  final bool availableTuesday;
  
  const AvailableTuesdayChanged({@required this.availableTuesday});
  
  @override
  List<Object> get props => [availableTuesday];
}
class AvailableWednesdayChanged extends EmployeeAvailabilityEvent {
  final bool availableWednesday;
  
  const AvailableWednesdayChanged({@required this.availableWednesday});
  
  @override
  List<Object> get props => [availableWednesday];
}
class AvailableThursdayChanged extends EmployeeAvailabilityEvent {
  final bool availableThursday;
  
  const AvailableThursdayChanged({@required this.availableThursday});
  
  @override
  List<Object> get props => [availableThursday];
}
class AvailableFridayChanged extends EmployeeAvailabilityEvent {
  final bool availableFriday;
  
  const AvailableFridayChanged({@required this.availableFriday});
  
  @override
  List<Object> get props => [availableFriday];
}
class AvailableSaturdayChanged extends EmployeeAvailabilityEvent {
  final bool availableSaturday;
  
  const AvailableSaturdayChanged({@required this.availableSaturday});
  
  @override
  List<Object> get props => [availableSaturday];
}
class AvailableSundayChanged extends EmployeeAvailabilityEvent {
  final bool availableSunday;
  
  const AvailableSundayChanged({@required this.availableSunday});
  
  @override
  List<Object> get props => [availableSunday];
}
class PreferredShiftChanged extends EmployeeAvailabilityEvent {
  final Shift preferredShift;
  
  const PreferredShiftChanged({@required this.preferredShift});
  
  @override
  List<Object> get props => [preferredShift];
}
class HasExtraDataChanged extends EmployeeAvailabilityEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}
class LastUpdatedDateChanged extends EmployeeAvailabilityEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends EmployeeAvailabilityEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}

class EmployeeAvailabilityFormSubmitted extends EmployeeAvailabilityEvent {}

class LoadEmployeeAvailabilityByIdForEdit extends EmployeeAvailabilityEvent {
  final int id;

  const LoadEmployeeAvailabilityByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteEmployeeAvailabilityById extends EmployeeAvailabilityEvent {
  final int id;

  const DeleteEmployeeAvailabilityById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadEmployeeAvailabilityByIdForView extends EmployeeAvailabilityEvent {
  final int id;

  const LoadEmployeeAvailabilityByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
