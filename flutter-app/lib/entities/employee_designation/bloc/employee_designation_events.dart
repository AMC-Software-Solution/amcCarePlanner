part of 'employee_designation_bloc.dart';

abstract class EmployeeDesignationEvent extends Equatable {
  const EmployeeDesignationEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitEmployeeDesignationList extends EmployeeDesignationEvent {}

class DesignationChanged extends EmployeeDesignationEvent {
  final String designation;
  
  const DesignationChanged({@required this.designation});
  
  @override
  List<Object> get props => [designation];
}
class DescriptionChanged extends EmployeeDesignationEvent {
  final String description;
  
  const DescriptionChanged({@required this.description});
  
  @override
  List<Object> get props => [description];
}
class DesignationDateChanged extends EmployeeDesignationEvent {
  final String designationDate;
  
  const DesignationDateChanged({@required this.designationDate});
  
  @override
  List<Object> get props => [designationDate];
}
class ClientIdChanged extends EmployeeDesignationEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends EmployeeDesignationEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class EmployeeDesignationFormSubmitted extends EmployeeDesignationEvent {}

class LoadEmployeeDesignationByIdForEdit extends EmployeeDesignationEvent {
  final int id;

  const LoadEmployeeDesignationByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteEmployeeDesignationById extends EmployeeDesignationEvent {
  final int id;

  const DeleteEmployeeDesignationById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadEmployeeDesignationByIdForView extends EmployeeDesignationEvent {
  final int id;

  const LoadEmployeeDesignationByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
