part of 'employee_location_bloc.dart';

abstract class EmployeeLocationEvent extends Equatable {
  const EmployeeLocationEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitEmployeeLocationList extends EmployeeLocationEvent {}

class LatitudeChanged extends EmployeeLocationEvent {
  final String latitude;
  
  const LatitudeChanged({@required this.latitude});
  
  @override
  List<Object> get props => [latitude];
}
class LongitudeChanged extends EmployeeLocationEvent {
  final String longitude;
  
  const LongitudeChanged({@required this.longitude});
  
  @override
  List<Object> get props => [longitude];
}
class CreatedDateChanged extends EmployeeLocationEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends EmployeeLocationEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends EmployeeLocationEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends EmployeeLocationEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class EmployeeLocationFormSubmitted extends EmployeeLocationEvent {}

class LoadEmployeeLocationByIdForEdit extends EmployeeLocationEvent {
  final int id;

  const LoadEmployeeLocationByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteEmployeeLocationById extends EmployeeLocationEvent {
  final int id;

  const DeleteEmployeeLocationById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadEmployeeLocationByIdForView extends EmployeeLocationEvent {
  final int id;

  const LoadEmployeeLocationByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
