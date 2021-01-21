part of 'service_user_bloc.dart';

enum ServiceUserStatusUI {init, loading, error, done}
enum ServiceUserDeleteStatus {ok, ko, none}

class ServiceUserState extends Equatable {
  final List<ServiceUser> serviceUsers;
  final ServiceUser loadedServiceUser;
  final bool editMode;
  final ServiceUserDeleteStatus deleteStatus;
  final ServiceUserStatusUI serviceUserStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final TitleInput title;
  final FirstNameInput firstName;
  final MiddleNameInput middleName;
  final LastNameInput lastName;
  final PreferredNameInput preferredName;
  final EmailInput email;
  final ServiceUserCodeInput serviceUserCode;
  final DateOfBirthInput dateOfBirth;
  final LastVisitDateInput lastVisitDate;
  final StartDateInput startDate;
  final SupportTypeInput supportType;
  final ServiceUserCategoryInput serviceUserCategory;
  final VulnerabilityInput vulnerability;
  final ServicePriorityInput servicePriority;
  final SourceInput source;
  final StatusInput status;
  final FirstLanguageInput firstLanguage;
  final InterpreterRequiredInput interpreterRequired;
  final ActivatedDateInput activatedDate;
  final ProfilePhotoUrlInput profilePhotoUrl;
  final LastRecordedHeightInput lastRecordedHeight;
  final LastRecordedWeightInput lastRecordedWeight;
  final HasMedicalConditionInput hasMedicalCondition;
  final MedicalConditionSummaryInput medicalConditionSummary;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  ServiceUserState(
DateOfBirthInput dateOfBirth,LastVisitDateInput lastVisitDate,StartDateInput startDate,ActivatedDateInput activatedDate,CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.serviceUsers = const [],
    this.serviceUserStatusUI = ServiceUserStatusUI.init,
    this.loadedServiceUser = const ServiceUser(0,null,'','','','','','',null,null,null,null,null,null,null,null,null,'',false,null,'','','',false,'',null,null,0,false,null,null,null,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = ServiceUserDeleteStatus.none,
    this.title = const TitleInput.pure(),
    this.firstName = const FirstNameInput.pure(),
    this.middleName = const MiddleNameInput.pure(),
    this.lastName = const LastNameInput.pure(),
    this.preferredName = const PreferredNameInput.pure(),
    this.email = const EmailInput.pure(),
    this.serviceUserCode = const ServiceUserCodeInput.pure(),
    this.supportType = const SupportTypeInput.pure(),
    this.serviceUserCategory = const ServiceUserCategoryInput.pure(),
    this.vulnerability = const VulnerabilityInput.pure(),
    this.servicePriority = const ServicePriorityInput.pure(),
    this.source = const SourceInput.pure(),
    this.status = const StatusInput.pure(),
    this.firstLanguage = const FirstLanguageInput.pure(),
    this.interpreterRequired = const InterpreterRequiredInput.pure(),
    this.profilePhotoUrl = const ProfilePhotoUrlInput.pure(),
    this.lastRecordedHeight = const LastRecordedHeightInput.pure(),
    this.lastRecordedWeight = const LastRecordedWeightInput.pure(),
    this.hasMedicalCondition = const HasMedicalConditionInput.pure(),
    this.medicalConditionSummary = const MedicalConditionSummaryInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.dateOfBirth = dateOfBirth ?? DateOfBirthInput.pure(),
this.lastVisitDate = lastVisitDate ?? LastVisitDateInput.pure(),
this.startDate = startDate ?? StartDateInput.pure(),
this.activatedDate = activatedDate ?? ActivatedDateInput.pure(),
this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  ServiceUserState copyWith({
    List<ServiceUser> serviceUsers,
    ServiceUserStatusUI serviceUserStatusUI,
    bool editMode,
    ServiceUserDeleteStatus deleteStatus,
    ServiceUser loadedServiceUser,
    FormzStatus formStatus,
    String generalNotificationKey,
    TitleInput title,
    FirstNameInput firstName,
    MiddleNameInput middleName,
    LastNameInput lastName,
    PreferredNameInput preferredName,
    EmailInput email,
    ServiceUserCodeInput serviceUserCode,
    DateOfBirthInput dateOfBirth,
    LastVisitDateInput lastVisitDate,
    StartDateInput startDate,
    SupportTypeInput supportType,
    ServiceUserCategoryInput serviceUserCategory,
    VulnerabilityInput vulnerability,
    ServicePriorityInput servicePriority,
    SourceInput source,
    StatusInput status,
    FirstLanguageInput firstLanguage,
    InterpreterRequiredInput interpreterRequired,
    ActivatedDateInput activatedDate,
    ProfilePhotoUrlInput profilePhotoUrl,
    LastRecordedHeightInput lastRecordedHeight,
    LastRecordedWeightInput lastRecordedWeight,
    HasMedicalConditionInput hasMedicalCondition,
    MedicalConditionSummaryInput medicalConditionSummary,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return ServiceUserState(
        dateOfBirth,
        lastVisitDate,
        startDate,
        activatedDate,
        createdDate,
        lastUpdatedDate,
      serviceUsers: serviceUsers ?? this.serviceUsers,
      serviceUserStatusUI: serviceUserStatusUI ?? this.serviceUserStatusUI,
      loadedServiceUser: loadedServiceUser ?? this.loadedServiceUser,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      title: title ?? this.title,
      firstName: firstName ?? this.firstName,
      middleName: middleName ?? this.middleName,
      lastName: lastName ?? this.lastName,
      preferredName: preferredName ?? this.preferredName,
      email: email ?? this.email,
      serviceUserCode: serviceUserCode ?? this.serviceUserCode,
      supportType: supportType ?? this.supportType,
      serviceUserCategory: serviceUserCategory ?? this.serviceUserCategory,
      vulnerability: vulnerability ?? this.vulnerability,
      servicePriority: servicePriority ?? this.servicePriority,
      source: source ?? this.source,
      status: status ?? this.status,
      firstLanguage: firstLanguage ?? this.firstLanguage,
      interpreterRequired: interpreterRequired ?? this.interpreterRequired,
      profilePhotoUrl: profilePhotoUrl ?? this.profilePhotoUrl,
      lastRecordedHeight: lastRecordedHeight ?? this.lastRecordedHeight,
      lastRecordedWeight: lastRecordedWeight ?? this.lastRecordedWeight,
      hasMedicalCondition: hasMedicalCondition ?? this.hasMedicalCondition,
      medicalConditionSummary: medicalConditionSummary ?? this.medicalConditionSummary,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [serviceUsers, serviceUserStatusUI,
     loadedServiceUser, editMode, deleteStatus, formStatus, generalNotificationKey, 
title,firstName,middleName,lastName,preferredName,email,serviceUserCode,dateOfBirth,lastVisitDate,startDate,supportType,serviceUserCategory,vulnerability,servicePriority,source,status,firstLanguage,interpreterRequired,activatedDate,profilePhotoUrl,lastRecordedHeight,lastRecordedWeight,hasMedicalCondition,medicalConditionSummary,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
