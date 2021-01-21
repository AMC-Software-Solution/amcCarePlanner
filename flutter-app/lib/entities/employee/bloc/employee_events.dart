part of 'employee_bloc.dart';

abstract class EmployeeEvent extends Equatable {
  const EmployeeEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitEmployeeList extends EmployeeEvent {}

class TitleChanged extends EmployeeEvent {
  final EmployeeTitle title;
  
  const TitleChanged({@required this.title});
  
  @override
  List<Object> get props => [title];
}
class FirstNameChanged extends EmployeeEvent {
  final String firstName;
  
  const FirstNameChanged({@required this.firstName});
  
  @override
  List<Object> get props => [firstName];
}
class MiddleInitialChanged extends EmployeeEvent {
  final String middleInitial;
  
  const MiddleInitialChanged({@required this.middleInitial});
  
  @override
  List<Object> get props => [middleInitial];
}
class LastNameChanged extends EmployeeEvent {
  final String lastName;
  
  const LastNameChanged({@required this.lastName});
  
  @override
  List<Object> get props => [lastName];
}
class PreferredNameChanged extends EmployeeEvent {
  final String preferredName;
  
  const PreferredNameChanged({@required this.preferredName});
  
  @override
  List<Object> get props => [preferredName];
}
class GenderChanged extends EmployeeEvent {
  final EmployeeGender gender;
  
  const GenderChanged({@required this.gender});
  
  @override
  List<Object> get props => [gender];
}
class EmployeeCodeChanged extends EmployeeEvent {
  final String employeeCode;
  
  const EmployeeCodeChanged({@required this.employeeCode});
  
  @override
  List<Object> get props => [employeeCode];
}
class PhoneChanged extends EmployeeEvent {
  final String phone;
  
  const PhoneChanged({@required this.phone});
  
  @override
  List<Object> get props => [phone];
}
class EmailChanged extends EmployeeEvent {
  final String email;
  
  const EmailChanged({@required this.email});
  
  @override
  List<Object> get props => [email];
}
class NationalInsuranceNumberChanged extends EmployeeEvent {
  final String nationalInsuranceNumber;
  
  const NationalInsuranceNumberChanged({@required this.nationalInsuranceNumber});
  
  @override
  List<Object> get props => [nationalInsuranceNumber];
}
class EmployeeContractTypeChanged extends EmployeeEvent {
  final EmployeeContractType employeeContractType;
  
  const EmployeeContractTypeChanged({@required this.employeeContractType});
  
  @override
  List<Object> get props => [employeeContractType];
}
class PinCodeChanged extends EmployeeEvent {
  final int pinCode;
  
  const PinCodeChanged({@required this.pinCode});
  
  @override
  List<Object> get props => [pinCode];
}
class TransportModeChanged extends EmployeeEvent {
  final EmployeeTravelMode transportMode;
  
  const TransportModeChanged({@required this.transportMode});
  
  @override
  List<Object> get props => [transportMode];
}
class AddressChanged extends EmployeeEvent {
  final String address;
  
  const AddressChanged({@required this.address});
  
  @override
  List<Object> get props => [address];
}
class CountyChanged extends EmployeeEvent {
  final String county;
  
  const CountyChanged({@required this.county});
  
  @override
  List<Object> get props => [county];
}
class PostCodeChanged extends EmployeeEvent {
  final String postCode;
  
  const PostCodeChanged({@required this.postCode});
  
  @override
  List<Object> get props => [postCode];
}
class DateOfBirthChanged extends EmployeeEvent {
  final DateTime dateOfBirth;
  
  const DateOfBirthChanged({@required this.dateOfBirth});
  
  @override
  List<Object> get props => [dateOfBirth];
}
class PhotoUrlChanged extends EmployeeEvent {
  final String photoUrl;
  
  const PhotoUrlChanged({@required this.photoUrl});
  
  @override
  List<Object> get props => [photoUrl];
}
class StatusChanged extends EmployeeEvent {
  final EmployeeStatus status;
  
  const StatusChanged({@required this.status});
  
  @override
  List<Object> get props => [status];
}
class EmployeeBioChanged extends EmployeeEvent {
  final String employeeBio;
  
  const EmployeeBioChanged({@required this.employeeBio});
  
  @override
  List<Object> get props => [employeeBio];
}
class AcruedHolidayHoursChanged extends EmployeeEvent {
  final int acruedHolidayHours;
  
  const AcruedHolidayHoursChanged({@required this.acruedHolidayHours});
  
  @override
  List<Object> get props => [acruedHolidayHours];
}
class CreatedDateChanged extends EmployeeEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends EmployeeEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends EmployeeEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends EmployeeEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class EmployeeFormSubmitted extends EmployeeEvent {}

class LoadEmployeeByIdForEdit extends EmployeeEvent {
  final int id;

  const LoadEmployeeByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteEmployeeById extends EmployeeEvent {
  final int id;

  const DeleteEmployeeById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadEmployeeByIdForView extends EmployeeEvent {
  final int id;

  const LoadEmployeeByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
