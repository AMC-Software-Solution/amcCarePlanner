part of 'service_user_bloc.dart';

abstract class ServiceUserEvent extends Equatable {
  const ServiceUserEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitServiceUserList extends ServiceUserEvent {}

class TitlleChanged extends ServiceUserEvent {
  final Titlle titlle;
  
  const TitlleChanged({@required this.titlle});
  
  @override
  List<Object> get props => [titlle];
}
class FirstNameChanged extends ServiceUserEvent {
  final String firstName;
  
  const FirstNameChanged({@required this.firstName});
  
  @override
  List<Object> get props => [firstName];
}
class MiddleNameChanged extends ServiceUserEvent {
  final String middleName;
  
  const MiddleNameChanged({@required this.middleName});
  
  @override
  List<Object> get props => [middleName];
}
class LastNameChanged extends ServiceUserEvent {
  final String lastName;
  
  const LastNameChanged({@required this.lastName});
  
  @override
  List<Object> get props => [lastName];
}
class PreferredNameChanged extends ServiceUserEvent {
  final String preferredName;
  
  const PreferredNameChanged({@required this.preferredName});
  
  @override
  List<Object> get props => [preferredName];
}
class EmailChanged extends ServiceUserEvent {
  final String email;
  
  const EmailChanged({@required this.email});
  
  @override
  List<Object> get props => [email];
}
class ServiceUserCodeChanged extends ServiceUserEvent {
  final String serviceUserCode;
  
  const ServiceUserCodeChanged({@required this.serviceUserCode});
  
  @override
  List<Object> get props => [serviceUserCode];
}
class DateOfBirthChanged extends ServiceUserEvent {
  final DateTime dateOfBirth;
  
  const DateOfBirthChanged({@required this.dateOfBirth});
  
  @override
  List<Object> get props => [dateOfBirth];
}
class LastVisitDateChanged extends ServiceUserEvent {
  final DateTime lastVisitDate;
  
  const LastVisitDateChanged({@required this.lastVisitDate});
  
  @override
  List<Object> get props => [lastVisitDate];
}
class StartDateChanged extends ServiceUserEvent {
  final DateTime startDate;
  
  const StartDateChanged({@required this.startDate});
  
  @override
  List<Object> get props => [startDate];
}
class SupportTypeChanged extends ServiceUserEvent {
  final SupportType supportType;
  
  const SupportTypeChanged({@required this.supportType});
  
  @override
  List<Object> get props => [supportType];
}
class ServiceUserCategoryChanged extends ServiceUserEvent {
  final ServiceUserCategory serviceUserCategory;
  
  const ServiceUserCategoryChanged({@required this.serviceUserCategory});
  
  @override
  List<Object> get props => [serviceUserCategory];
}
class VulnerabilityChanged extends ServiceUserEvent {
  final Vulnerability vulnerability;
  
  const VulnerabilityChanged({@required this.vulnerability});
  
  @override
  List<Object> get props => [vulnerability];
}
class ServicePriorityChanged extends ServiceUserEvent {
  final ServicePriority servicePriority;
  
  const ServicePriorityChanged({@required this.servicePriority});
  
  @override
  List<Object> get props => [servicePriority];
}
class SourceChanged extends ServiceUserEvent {
  final Source source;
  
  const SourceChanged({@required this.source});
  
  @override
  List<Object> get props => [source];
}
class StatusChanged extends ServiceUserEvent {
  final ServiceUserStatus status;
  
  const StatusChanged({@required this.status});
  
  @override
  List<Object> get props => [status];
}
class FirstLanguageChanged extends ServiceUserEvent {
  final String firstLanguage;
  
  const FirstLanguageChanged({@required this.firstLanguage});
  
  @override
  List<Object> get props => [firstLanguage];
}
class InterpreterRequiredChanged extends ServiceUserEvent {
  final bool interpreterRequired;
  
  const InterpreterRequiredChanged({@required this.interpreterRequired});
  
  @override
  List<Object> get props => [interpreterRequired];
}
class ActivatedDateChanged extends ServiceUserEvent {
  final DateTime activatedDate;
  
  const ActivatedDateChanged({@required this.activatedDate});
  
  @override
  List<Object> get props => [activatedDate];
}
class ProfilePhotoUrlChanged extends ServiceUserEvent {
  final String profilePhotoUrl;
  
  const ProfilePhotoUrlChanged({@required this.profilePhotoUrl});
  
  @override
  List<Object> get props => [profilePhotoUrl];
}
class LastRecordedHeightChanged extends ServiceUserEvent {
  final String lastRecordedHeight;
  
  const LastRecordedHeightChanged({@required this.lastRecordedHeight});
  
  @override
  List<Object> get props => [lastRecordedHeight];
}
class LastRecordedWeightChanged extends ServiceUserEvent {
  final String lastRecordedWeight;
  
  const LastRecordedWeightChanged({@required this.lastRecordedWeight});
  
  @override
  List<Object> get props => [lastRecordedWeight];
}
class HasMedicalConditionChanged extends ServiceUserEvent {
  final bool hasMedicalCondition;
  
  const HasMedicalConditionChanged({@required this.hasMedicalCondition});
  
  @override
  List<Object> get props => [hasMedicalCondition];
}
class MedicalConditionSummaryChanged extends ServiceUserEvent {
  final String medicalConditionSummary;
  
  const MedicalConditionSummaryChanged({@required this.medicalConditionSummary});
  
  @override
  List<Object> get props => [medicalConditionSummary];
}
class CreatedDateChanged extends ServiceUserEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends ServiceUserEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends ServiceUserEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends ServiceUserEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class ServiceUserFormSubmitted extends ServiceUserEvent {}

class LoadServiceUserByIdForEdit extends ServiceUserEvent {
  final int id;

  const LoadServiceUserByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteServiceUserById extends ServiceUserEvent {
  final int id;

  const DeleteServiceUserById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadServiceUserByIdForView extends ServiceUserEvent {
  final int id;

  const LoadServiceUserByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
