import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/service_user/service_user_model.dart';
import 'package:amcCarePlanner/entities/service_user/service_user_repository.dart';
import 'package:amcCarePlanner/entities/service_user/bloc/service_user_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'service_user_events.dart';
part 'service_user_state.dart';

class ServiceUserBloc extends Bloc<ServiceUserEvent, ServiceUserState> {
  final ServiceUserRepository _serviceUserRepository;

  final firstNameController = TextEditingController();
  final middleNameController = TextEditingController();
  final lastNameController = TextEditingController();
  final preferredNameController = TextEditingController();
  final emailController = TextEditingController();
  final serviceUserCodeController = TextEditingController();
  final dateOfBirthController = TextEditingController();
  final lastVisitDateController = TextEditingController();
  final startDateController = TextEditingController();
  final firstLanguageController = TextEditingController();
  final activatedDateController = TextEditingController();
  final profilePhotoUrlController = TextEditingController();
  final lastRecordedHeightController = TextEditingController();
  final lastRecordedWeightController = TextEditingController();
  final medicalConditionSummaryController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  ServiceUserBloc({@required ServiceUserRepository serviceUserRepository}) : assert(serviceUserRepository != null),
        _serviceUserRepository = serviceUserRepository, 
  super(ServiceUserState(null,null,null,null,null,null,));

  @override
  void onTransition(Transition<ServiceUserEvent, ServiceUserState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<ServiceUserState> mapEventToState(ServiceUserEvent event) async* {
    if (event is InitServiceUserList) {
      yield* onInitList(event);
    } else if (event is ServiceUserFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadServiceUserByIdForEdit) {
      yield* onLoadServiceUserIdForEdit(event);
    } else if (event is DeleteServiceUserById) {
      yield* onDeleteServiceUserId(event);
    } else if (event is LoadServiceUserByIdForView) {
      yield* onLoadServiceUserIdForView(event);
    }else if (event is TitlleChanged){
      yield* onTitlleChange(event);
    }else if (event is FirstNameChanged){
      yield* onFirstNameChange(event);
    }else if (event is MiddleNameChanged){
      yield* onMiddleNameChange(event);
    }else if (event is LastNameChanged){
      yield* onLastNameChange(event);
    }else if (event is PreferredNameChanged){
      yield* onPreferredNameChange(event);
    }else if (event is EmailChanged){
      yield* onEmailChange(event);
    }else if (event is ServiceUserCodeChanged){
      yield* onServiceUserCodeChange(event);
    }else if (event is DateOfBirthChanged){
      yield* onDateOfBirthChange(event);
    }else if (event is LastVisitDateChanged){
      yield* onLastVisitDateChange(event);
    }else if (event is StartDateChanged){
      yield* onStartDateChange(event);
    }else if (event is SupportTypeChanged){
      yield* onSupportTypeChange(event);
    }else if (event is ServiceUserCategoryChanged){
      yield* onServiceUserCategoryChange(event);
    }else if (event is VulnerabilityChanged){
      yield* onVulnerabilityChange(event);
    }else if (event is ServicePriorityChanged){
      yield* onServicePriorityChange(event);
    }else if (event is SourceChanged){
      yield* onSourceChange(event);
    }else if (event is StatusChanged){
      yield* onStatusChange(event);
    }else if (event is FirstLanguageChanged){
      yield* onFirstLanguageChange(event);
    }else if (event is InterpreterRequiredChanged){
      yield* onInterpreterRequiredChange(event);
    }else if (event is ActivatedDateChanged){
      yield* onActivatedDateChange(event);
    }else if (event is ProfilePhotoUrlChanged){
      yield* onProfilePhotoUrlChange(event);
    }else if (event is LastRecordedHeightChanged){
      yield* onLastRecordedHeightChange(event);
    }else if (event is LastRecordedWeightChanged){
      yield* onLastRecordedWeightChange(event);
    }else if (event is HasMedicalConditionChanged){
      yield* onHasMedicalConditionChange(event);
    }else if (event is MedicalConditionSummaryChanged){
      yield* onMedicalConditionSummaryChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<ServiceUserState> onInitList(InitServiceUserList event) async* {
    yield this.state.copyWith(serviceUserStatusUI: ServiceUserStatusUI.loading);
    List<ServiceUser> serviceUsers = await _serviceUserRepository.getAllServiceUsers();
    yield this.state.copyWith(serviceUsers: serviceUsers, serviceUserStatusUI: ServiceUserStatusUI.done);
  }

  Stream<ServiceUserState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        ServiceUser result;
        if(this.state.editMode) {
          ServiceUser newServiceUser = ServiceUser(state.loadedServiceUser.id,
            this.state.titlle.value,  
            this.state.firstName.value,  
            this.state.middleName.value,  
            this.state.lastName.value,  
            this.state.preferredName.value,  
            this.state.email.value,  
            this.state.serviceUserCode.value,  
            this.state.dateOfBirth.value,  
            this.state.lastVisitDate.value,  
            this.state.startDate.value,  
            this.state.supportType.value,  
            this.state.serviceUserCategory.value,  
            this.state.vulnerability.value,  
            this.state.servicePriority.value,  
            this.state.source.value,  
            this.state.status.value,  
            this.state.firstLanguage.value,  
            this.state.interpreterRequired.value,  
            this.state.activatedDate.value,  
            this.state.profilePhotoUrl.value,  
            this.state.lastRecordedHeight.value,  
            this.state.lastRecordedWeight.value,  
            this.state.hasMedicalCondition.value,  
            this.state.medicalConditionSummary.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
            null, 
            null, 
          );

          result = await _serviceUserRepository.update(newServiceUser);
        } else {
          ServiceUser newServiceUser = ServiceUser(null,
            this.state.titlle.value,  
            this.state.firstName.value,  
            this.state.middleName.value,  
            this.state.lastName.value,  
            this.state.preferredName.value,  
            this.state.email.value,  
            this.state.serviceUserCode.value,  
            this.state.dateOfBirth.value,  
            this.state.lastVisitDate.value,  
            this.state.startDate.value,  
            this.state.supportType.value,  
            this.state.serviceUserCategory.value,  
            this.state.vulnerability.value,  
            this.state.servicePriority.value,  
            this.state.source.value,  
            this.state.status.value,  
            this.state.firstLanguage.value,  
            this.state.interpreterRequired.value,  
            this.state.activatedDate.value,  
            this.state.profilePhotoUrl.value,  
            this.state.lastRecordedHeight.value,  
            this.state.lastRecordedWeight.value,  
            this.state.hasMedicalCondition.value,  
            this.state.medicalConditionSummary.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
            null, 
            null, 
          );

          result = await _serviceUserRepository.create(newServiceUser);
        }

        if (result == null) {
          yield this.state.copyWith(formStatus: FormzStatus.submissionFailure,
              generalNotificationKey: HttpUtils.badRequestServerKey);
        } else {
          yield this.state.copyWith(formStatus: FormzStatus.submissionSuccess,
              generalNotificationKey: HttpUtils.successResult);
        }
      } catch (e) {
        yield this.state.copyWith(formStatus: FormzStatus.submissionFailure,
            generalNotificationKey: HttpUtils.errorServerKey);
      }
    }
  }

  Stream<ServiceUserState> onLoadServiceUserIdForEdit(LoadServiceUserByIdForEdit event) async* {
    yield this.state.copyWith(serviceUserStatusUI: ServiceUserStatusUI.loading);
    ServiceUser loadedServiceUser = await _serviceUserRepository.getServiceUser(event.id);

    final titlle = TitlleInput.dirty(loadedServiceUser?.titlle != null ? loadedServiceUser.titlle: null);
    final firstName = FirstNameInput.dirty(loadedServiceUser?.firstName != null ? loadedServiceUser.firstName: '');
    final middleName = MiddleNameInput.dirty(loadedServiceUser?.middleName != null ? loadedServiceUser.middleName: '');
    final lastName = LastNameInput.dirty(loadedServiceUser?.lastName != null ? loadedServiceUser.lastName: '');
    final preferredName = PreferredNameInput.dirty(loadedServiceUser?.preferredName != null ? loadedServiceUser.preferredName: '');
    final email = EmailInput.dirty(loadedServiceUser?.email != null ? loadedServiceUser.email: '');
    final serviceUserCode = ServiceUserCodeInput.dirty(loadedServiceUser?.serviceUserCode != null ? loadedServiceUser.serviceUserCode: '');
    final dateOfBirth = DateOfBirthInput.dirty(loadedServiceUser?.dateOfBirth != null ? loadedServiceUser.dateOfBirth: null);
    final lastVisitDate = LastVisitDateInput.dirty(loadedServiceUser?.lastVisitDate != null ? loadedServiceUser.lastVisitDate: null);
    final startDate = StartDateInput.dirty(loadedServiceUser?.startDate != null ? loadedServiceUser.startDate: null);
    final supportType = SupportTypeInput.dirty(loadedServiceUser?.supportType != null ? loadedServiceUser.supportType: null);
    final serviceUserCategory = ServiceUserCategoryInput.dirty(loadedServiceUser?.serviceUserCategory != null ? loadedServiceUser.serviceUserCategory: null);
    final vulnerability = VulnerabilityInput.dirty(loadedServiceUser?.vulnerability != null ? loadedServiceUser.vulnerability: null);
    final servicePriority = ServicePriorityInput.dirty(loadedServiceUser?.servicePriority != null ? loadedServiceUser.servicePriority: null);
    final source = SourceInput.dirty(loadedServiceUser?.source != null ? loadedServiceUser.source: null);
    final status = StatusInput.dirty(loadedServiceUser?.status != null ? loadedServiceUser.status: null);
    final firstLanguage = FirstLanguageInput.dirty(loadedServiceUser?.firstLanguage != null ? loadedServiceUser.firstLanguage: '');
    final interpreterRequired = InterpreterRequiredInput.dirty(loadedServiceUser?.interpreterRequired != null ? loadedServiceUser.interpreterRequired: false);
    final activatedDate = ActivatedDateInput.dirty(loadedServiceUser?.activatedDate != null ? loadedServiceUser.activatedDate: null);
    final profilePhotoUrl = ProfilePhotoUrlInput.dirty(loadedServiceUser?.profilePhotoUrl != null ? loadedServiceUser.profilePhotoUrl: '');
    final lastRecordedHeight = LastRecordedHeightInput.dirty(loadedServiceUser?.lastRecordedHeight != null ? loadedServiceUser.lastRecordedHeight: '');
    final lastRecordedWeight = LastRecordedWeightInput.dirty(loadedServiceUser?.lastRecordedWeight != null ? loadedServiceUser.lastRecordedWeight: '');
    final hasMedicalCondition = HasMedicalConditionInput.dirty(loadedServiceUser?.hasMedicalCondition != null ? loadedServiceUser.hasMedicalCondition: false);
    final medicalConditionSummary = MedicalConditionSummaryInput.dirty(loadedServiceUser?.medicalConditionSummary != null ? loadedServiceUser.medicalConditionSummary: '');
    final createdDate = CreatedDateInput.dirty(loadedServiceUser?.createdDate != null ? loadedServiceUser.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedServiceUser?.lastUpdatedDate != null ? loadedServiceUser.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedServiceUser?.clientId != null ? loadedServiceUser.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedServiceUser?.hasExtraData != null ? loadedServiceUser.hasExtraData: false);

    yield this.state.copyWith(loadedServiceUser: loadedServiceUser, editMode: true,
      titlle: titlle,
      firstName: firstName,
      middleName: middleName,
      lastName: lastName,
      preferredName: preferredName,
      email: email,
      serviceUserCode: serviceUserCode,
      dateOfBirth: dateOfBirth,
      lastVisitDate: lastVisitDate,
      startDate: startDate,
      supportType: supportType,
      serviceUserCategory: serviceUserCategory,
      vulnerability: vulnerability,
      servicePriority: servicePriority,
      source: source,
      status: status,
      firstLanguage: firstLanguage,
      interpreterRequired: interpreterRequired,
      activatedDate: activatedDate,
      profilePhotoUrl: profilePhotoUrl,
      lastRecordedHeight: lastRecordedHeight,
      lastRecordedWeight: lastRecordedWeight,
      hasMedicalCondition: hasMedicalCondition,
      medicalConditionSummary: medicalConditionSummary,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    serviceUserStatusUI: ServiceUserStatusUI.done);

    firstNameController.text = loadedServiceUser.firstName;
    middleNameController.text = loadedServiceUser.middleName;
    lastNameController.text = loadedServiceUser.lastName;
    preferredNameController.text = loadedServiceUser.preferredName;
    emailController.text = loadedServiceUser.email;
    serviceUserCodeController.text = loadedServiceUser.serviceUserCode;
    dateOfBirthController.text = DateFormat.yMMMMd(S.current.locale).format(loadedServiceUser?.dateOfBirth);
    lastVisitDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedServiceUser?.lastVisitDate);
    startDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedServiceUser?.startDate);
    firstLanguageController.text = loadedServiceUser.firstLanguage;
    activatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedServiceUser?.activatedDate);
    profilePhotoUrlController.text = loadedServiceUser.profilePhotoUrl;
    lastRecordedHeightController.text = loadedServiceUser.lastRecordedHeight;
    lastRecordedWeightController.text = loadedServiceUser.lastRecordedWeight;
    medicalConditionSummaryController.text = loadedServiceUser.medicalConditionSummary;
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedServiceUser?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedServiceUser?.lastUpdatedDate);
    clientIdController.text = loadedServiceUser.clientId.toString();
  }

  Stream<ServiceUserState> onDeleteServiceUserId(DeleteServiceUserById event) async* {
    try {
      await _serviceUserRepository.delete(event.id);
      this.add(InitServiceUserList());
      yield this.state.copyWith(deleteStatus: ServiceUserDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: ServiceUserDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: ServiceUserDeleteStatus.none);
  }

  Stream<ServiceUserState> onLoadServiceUserIdForView(LoadServiceUserByIdForView event) async* {
    yield this.state.copyWith(serviceUserStatusUI: ServiceUserStatusUI.loading);
    try {
      ServiceUser loadedServiceUser = await _serviceUserRepository.getServiceUser(event.id);
      yield this.state.copyWith(loadedServiceUser: loadedServiceUser, serviceUserStatusUI: ServiceUserStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedServiceUser: null, serviceUserStatusUI: ServiceUserStatusUI.error);
    }
  }


  Stream<ServiceUserState> onTitlleChange(TitlleChanged event) async* {
    final titlle = TitlleInput.dirty(event.titlle);
    yield this.state.copyWith(
      titlle: titlle,
      formStatus: Formz.validate([
        titlle,
      this.state.firstName,
      this.state.middleName,
      this.state.lastName,
      this.state.preferredName,
      this.state.email,
      this.state.serviceUserCode,
      this.state.dateOfBirth,
      this.state.lastVisitDate,
      this.state.startDate,
      this.state.supportType,
      this.state.serviceUserCategory,
      this.state.vulnerability,
      this.state.servicePriority,
      this.state.source,
      this.state.status,
      this.state.firstLanguage,
      this.state.interpreterRequired,
      this.state.activatedDate,
      this.state.profilePhotoUrl,
      this.state.lastRecordedHeight,
      this.state.lastRecordedWeight,
      this.state.hasMedicalCondition,
      this.state.medicalConditionSummary,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserState> onFirstNameChange(FirstNameChanged event) async* {
    final firstName = FirstNameInput.dirty(event.firstName);
    yield this.state.copyWith(
      firstName: firstName,
      formStatus: Formz.validate([
      this.state.titlle,
        firstName,
      this.state.middleName,
      this.state.lastName,
      this.state.preferredName,
      this.state.email,
      this.state.serviceUserCode,
      this.state.dateOfBirth,
      this.state.lastVisitDate,
      this.state.startDate,
      this.state.supportType,
      this.state.serviceUserCategory,
      this.state.vulnerability,
      this.state.servicePriority,
      this.state.source,
      this.state.status,
      this.state.firstLanguage,
      this.state.interpreterRequired,
      this.state.activatedDate,
      this.state.profilePhotoUrl,
      this.state.lastRecordedHeight,
      this.state.lastRecordedWeight,
      this.state.hasMedicalCondition,
      this.state.medicalConditionSummary,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserState> onMiddleNameChange(MiddleNameChanged event) async* {
    final middleName = MiddleNameInput.dirty(event.middleName);
    yield this.state.copyWith(
      middleName: middleName,
      formStatus: Formz.validate([
      this.state.titlle,
      this.state.firstName,
        middleName,
      this.state.lastName,
      this.state.preferredName,
      this.state.email,
      this.state.serviceUserCode,
      this.state.dateOfBirth,
      this.state.lastVisitDate,
      this.state.startDate,
      this.state.supportType,
      this.state.serviceUserCategory,
      this.state.vulnerability,
      this.state.servicePriority,
      this.state.source,
      this.state.status,
      this.state.firstLanguage,
      this.state.interpreterRequired,
      this.state.activatedDate,
      this.state.profilePhotoUrl,
      this.state.lastRecordedHeight,
      this.state.lastRecordedWeight,
      this.state.hasMedicalCondition,
      this.state.medicalConditionSummary,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserState> onLastNameChange(LastNameChanged event) async* {
    final lastName = LastNameInput.dirty(event.lastName);
    yield this.state.copyWith(
      lastName: lastName,
      formStatus: Formz.validate([
      this.state.titlle,
      this.state.firstName,
      this.state.middleName,
        lastName,
      this.state.preferredName,
      this.state.email,
      this.state.serviceUserCode,
      this.state.dateOfBirth,
      this.state.lastVisitDate,
      this.state.startDate,
      this.state.supportType,
      this.state.serviceUserCategory,
      this.state.vulnerability,
      this.state.servicePriority,
      this.state.source,
      this.state.status,
      this.state.firstLanguage,
      this.state.interpreterRequired,
      this.state.activatedDate,
      this.state.profilePhotoUrl,
      this.state.lastRecordedHeight,
      this.state.lastRecordedWeight,
      this.state.hasMedicalCondition,
      this.state.medicalConditionSummary,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserState> onPreferredNameChange(PreferredNameChanged event) async* {
    final preferredName = PreferredNameInput.dirty(event.preferredName);
    yield this.state.copyWith(
      preferredName: preferredName,
      formStatus: Formz.validate([
      this.state.titlle,
      this.state.firstName,
      this.state.middleName,
      this.state.lastName,
        preferredName,
      this.state.email,
      this.state.serviceUserCode,
      this.state.dateOfBirth,
      this.state.lastVisitDate,
      this.state.startDate,
      this.state.supportType,
      this.state.serviceUserCategory,
      this.state.vulnerability,
      this.state.servicePriority,
      this.state.source,
      this.state.status,
      this.state.firstLanguage,
      this.state.interpreterRequired,
      this.state.activatedDate,
      this.state.profilePhotoUrl,
      this.state.lastRecordedHeight,
      this.state.lastRecordedWeight,
      this.state.hasMedicalCondition,
      this.state.medicalConditionSummary,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserState> onEmailChange(EmailChanged event) async* {
    final email = EmailInput.dirty(event.email);
    yield this.state.copyWith(
      email: email,
      formStatus: Formz.validate([
      this.state.titlle,
      this.state.firstName,
      this.state.middleName,
      this.state.lastName,
      this.state.preferredName,
        email,
      this.state.serviceUserCode,
      this.state.dateOfBirth,
      this.state.lastVisitDate,
      this.state.startDate,
      this.state.supportType,
      this.state.serviceUserCategory,
      this.state.vulnerability,
      this.state.servicePriority,
      this.state.source,
      this.state.status,
      this.state.firstLanguage,
      this.state.interpreterRequired,
      this.state.activatedDate,
      this.state.profilePhotoUrl,
      this.state.lastRecordedHeight,
      this.state.lastRecordedWeight,
      this.state.hasMedicalCondition,
      this.state.medicalConditionSummary,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserState> onServiceUserCodeChange(ServiceUserCodeChanged event) async* {
    final serviceUserCode = ServiceUserCodeInput.dirty(event.serviceUserCode);
    yield this.state.copyWith(
      serviceUserCode: serviceUserCode,
      formStatus: Formz.validate([
      this.state.titlle,
      this.state.firstName,
      this.state.middleName,
      this.state.lastName,
      this.state.preferredName,
      this.state.email,
        serviceUserCode,
      this.state.dateOfBirth,
      this.state.lastVisitDate,
      this.state.startDate,
      this.state.supportType,
      this.state.serviceUserCategory,
      this.state.vulnerability,
      this.state.servicePriority,
      this.state.source,
      this.state.status,
      this.state.firstLanguage,
      this.state.interpreterRequired,
      this.state.activatedDate,
      this.state.profilePhotoUrl,
      this.state.lastRecordedHeight,
      this.state.lastRecordedWeight,
      this.state.hasMedicalCondition,
      this.state.medicalConditionSummary,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserState> onDateOfBirthChange(DateOfBirthChanged event) async* {
    final dateOfBirth = DateOfBirthInput.dirty(event.dateOfBirth);
    yield this.state.copyWith(
      dateOfBirth: dateOfBirth,
      formStatus: Formz.validate([
      this.state.titlle,
      this.state.firstName,
      this.state.middleName,
      this.state.lastName,
      this.state.preferredName,
      this.state.email,
      this.state.serviceUserCode,
        dateOfBirth,
      this.state.lastVisitDate,
      this.state.startDate,
      this.state.supportType,
      this.state.serviceUserCategory,
      this.state.vulnerability,
      this.state.servicePriority,
      this.state.source,
      this.state.status,
      this.state.firstLanguage,
      this.state.interpreterRequired,
      this.state.activatedDate,
      this.state.profilePhotoUrl,
      this.state.lastRecordedHeight,
      this.state.lastRecordedWeight,
      this.state.hasMedicalCondition,
      this.state.medicalConditionSummary,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserState> onLastVisitDateChange(LastVisitDateChanged event) async* {
    final lastVisitDate = LastVisitDateInput.dirty(event.lastVisitDate);
    yield this.state.copyWith(
      lastVisitDate: lastVisitDate,
      formStatus: Formz.validate([
      this.state.titlle,
      this.state.firstName,
      this.state.middleName,
      this.state.lastName,
      this.state.preferredName,
      this.state.email,
      this.state.serviceUserCode,
      this.state.dateOfBirth,
        lastVisitDate,
      this.state.startDate,
      this.state.supportType,
      this.state.serviceUserCategory,
      this.state.vulnerability,
      this.state.servicePriority,
      this.state.source,
      this.state.status,
      this.state.firstLanguage,
      this.state.interpreterRequired,
      this.state.activatedDate,
      this.state.profilePhotoUrl,
      this.state.lastRecordedHeight,
      this.state.lastRecordedWeight,
      this.state.hasMedicalCondition,
      this.state.medicalConditionSummary,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserState> onStartDateChange(StartDateChanged event) async* {
    final startDate = StartDateInput.dirty(event.startDate);
    yield this.state.copyWith(
      startDate: startDate,
      formStatus: Formz.validate([
      this.state.titlle,
      this.state.firstName,
      this.state.middleName,
      this.state.lastName,
      this.state.preferredName,
      this.state.email,
      this.state.serviceUserCode,
      this.state.dateOfBirth,
      this.state.lastVisitDate,
        startDate,
      this.state.supportType,
      this.state.serviceUserCategory,
      this.state.vulnerability,
      this.state.servicePriority,
      this.state.source,
      this.state.status,
      this.state.firstLanguage,
      this.state.interpreterRequired,
      this.state.activatedDate,
      this.state.profilePhotoUrl,
      this.state.lastRecordedHeight,
      this.state.lastRecordedWeight,
      this.state.hasMedicalCondition,
      this.state.medicalConditionSummary,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserState> onSupportTypeChange(SupportTypeChanged event) async* {
    final supportType = SupportTypeInput.dirty(event.supportType);
    yield this.state.copyWith(
      supportType: supportType,
      formStatus: Formz.validate([
      this.state.titlle,
      this.state.firstName,
      this.state.middleName,
      this.state.lastName,
      this.state.preferredName,
      this.state.email,
      this.state.serviceUserCode,
      this.state.dateOfBirth,
      this.state.lastVisitDate,
      this.state.startDate,
        supportType,
      this.state.serviceUserCategory,
      this.state.vulnerability,
      this.state.servicePriority,
      this.state.source,
      this.state.status,
      this.state.firstLanguage,
      this.state.interpreterRequired,
      this.state.activatedDate,
      this.state.profilePhotoUrl,
      this.state.lastRecordedHeight,
      this.state.lastRecordedWeight,
      this.state.hasMedicalCondition,
      this.state.medicalConditionSummary,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserState> onServiceUserCategoryChange(ServiceUserCategoryChanged event) async* {
    final serviceUserCategory = ServiceUserCategoryInput.dirty(event.serviceUserCategory);
    yield this.state.copyWith(
      serviceUserCategory: serviceUserCategory,
      formStatus: Formz.validate([
      this.state.titlle,
      this.state.firstName,
      this.state.middleName,
      this.state.lastName,
      this.state.preferredName,
      this.state.email,
      this.state.serviceUserCode,
      this.state.dateOfBirth,
      this.state.lastVisitDate,
      this.state.startDate,
      this.state.supportType,
        serviceUserCategory,
      this.state.vulnerability,
      this.state.servicePriority,
      this.state.source,
      this.state.status,
      this.state.firstLanguage,
      this.state.interpreterRequired,
      this.state.activatedDate,
      this.state.profilePhotoUrl,
      this.state.lastRecordedHeight,
      this.state.lastRecordedWeight,
      this.state.hasMedicalCondition,
      this.state.medicalConditionSummary,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserState> onVulnerabilityChange(VulnerabilityChanged event) async* {
    final vulnerability = VulnerabilityInput.dirty(event.vulnerability);
    yield this.state.copyWith(
      vulnerability: vulnerability,
      formStatus: Formz.validate([
      this.state.titlle,
      this.state.firstName,
      this.state.middleName,
      this.state.lastName,
      this.state.preferredName,
      this.state.email,
      this.state.serviceUserCode,
      this.state.dateOfBirth,
      this.state.lastVisitDate,
      this.state.startDate,
      this.state.supportType,
      this.state.serviceUserCategory,
        vulnerability,
      this.state.servicePriority,
      this.state.source,
      this.state.status,
      this.state.firstLanguage,
      this.state.interpreterRequired,
      this.state.activatedDate,
      this.state.profilePhotoUrl,
      this.state.lastRecordedHeight,
      this.state.lastRecordedWeight,
      this.state.hasMedicalCondition,
      this.state.medicalConditionSummary,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserState> onServicePriorityChange(ServicePriorityChanged event) async* {
    final servicePriority = ServicePriorityInput.dirty(event.servicePriority);
    yield this.state.copyWith(
      servicePriority: servicePriority,
      formStatus: Formz.validate([
      this.state.titlle,
      this.state.firstName,
      this.state.middleName,
      this.state.lastName,
      this.state.preferredName,
      this.state.email,
      this.state.serviceUserCode,
      this.state.dateOfBirth,
      this.state.lastVisitDate,
      this.state.startDate,
      this.state.supportType,
      this.state.serviceUserCategory,
      this.state.vulnerability,
        servicePriority,
      this.state.source,
      this.state.status,
      this.state.firstLanguage,
      this.state.interpreterRequired,
      this.state.activatedDate,
      this.state.profilePhotoUrl,
      this.state.lastRecordedHeight,
      this.state.lastRecordedWeight,
      this.state.hasMedicalCondition,
      this.state.medicalConditionSummary,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserState> onSourceChange(SourceChanged event) async* {
    final source = SourceInput.dirty(event.source);
    yield this.state.copyWith(
      source: source,
      formStatus: Formz.validate([
      this.state.titlle,
      this.state.firstName,
      this.state.middleName,
      this.state.lastName,
      this.state.preferredName,
      this.state.email,
      this.state.serviceUserCode,
      this.state.dateOfBirth,
      this.state.lastVisitDate,
      this.state.startDate,
      this.state.supportType,
      this.state.serviceUserCategory,
      this.state.vulnerability,
      this.state.servicePriority,
        source,
      this.state.status,
      this.state.firstLanguage,
      this.state.interpreterRequired,
      this.state.activatedDate,
      this.state.profilePhotoUrl,
      this.state.lastRecordedHeight,
      this.state.lastRecordedWeight,
      this.state.hasMedicalCondition,
      this.state.medicalConditionSummary,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserState> onStatusChange(StatusChanged event) async* {
    final status = StatusInput.dirty(event.status);
    yield this.state.copyWith(
      status: status,
      formStatus: Formz.validate([
      this.state.titlle,
      this.state.firstName,
      this.state.middleName,
      this.state.lastName,
      this.state.preferredName,
      this.state.email,
      this.state.serviceUserCode,
      this.state.dateOfBirth,
      this.state.lastVisitDate,
      this.state.startDate,
      this.state.supportType,
      this.state.serviceUserCategory,
      this.state.vulnerability,
      this.state.servicePriority,
      this.state.source,
        status,
      this.state.firstLanguage,
      this.state.interpreterRequired,
      this.state.activatedDate,
      this.state.profilePhotoUrl,
      this.state.lastRecordedHeight,
      this.state.lastRecordedWeight,
      this.state.hasMedicalCondition,
      this.state.medicalConditionSummary,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserState> onFirstLanguageChange(FirstLanguageChanged event) async* {
    final firstLanguage = FirstLanguageInput.dirty(event.firstLanguage);
    yield this.state.copyWith(
      firstLanguage: firstLanguage,
      formStatus: Formz.validate([
      this.state.titlle,
      this.state.firstName,
      this.state.middleName,
      this.state.lastName,
      this.state.preferredName,
      this.state.email,
      this.state.serviceUserCode,
      this.state.dateOfBirth,
      this.state.lastVisitDate,
      this.state.startDate,
      this.state.supportType,
      this.state.serviceUserCategory,
      this.state.vulnerability,
      this.state.servicePriority,
      this.state.source,
      this.state.status,
        firstLanguage,
      this.state.interpreterRequired,
      this.state.activatedDate,
      this.state.profilePhotoUrl,
      this.state.lastRecordedHeight,
      this.state.lastRecordedWeight,
      this.state.hasMedicalCondition,
      this.state.medicalConditionSummary,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserState> onInterpreterRequiredChange(InterpreterRequiredChanged event) async* {
    final interpreterRequired = InterpreterRequiredInput.dirty(event.interpreterRequired);
    yield this.state.copyWith(
      interpreterRequired: interpreterRequired,
      formStatus: Formz.validate([
      this.state.titlle,
      this.state.firstName,
      this.state.middleName,
      this.state.lastName,
      this.state.preferredName,
      this.state.email,
      this.state.serviceUserCode,
      this.state.dateOfBirth,
      this.state.lastVisitDate,
      this.state.startDate,
      this.state.supportType,
      this.state.serviceUserCategory,
      this.state.vulnerability,
      this.state.servicePriority,
      this.state.source,
      this.state.status,
      this.state.firstLanguage,
        interpreterRequired,
      this.state.activatedDate,
      this.state.profilePhotoUrl,
      this.state.lastRecordedHeight,
      this.state.lastRecordedWeight,
      this.state.hasMedicalCondition,
      this.state.medicalConditionSummary,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserState> onActivatedDateChange(ActivatedDateChanged event) async* {
    final activatedDate = ActivatedDateInput.dirty(event.activatedDate);
    yield this.state.copyWith(
      activatedDate: activatedDate,
      formStatus: Formz.validate([
      this.state.titlle,
      this.state.firstName,
      this.state.middleName,
      this.state.lastName,
      this.state.preferredName,
      this.state.email,
      this.state.serviceUserCode,
      this.state.dateOfBirth,
      this.state.lastVisitDate,
      this.state.startDate,
      this.state.supportType,
      this.state.serviceUserCategory,
      this.state.vulnerability,
      this.state.servicePriority,
      this.state.source,
      this.state.status,
      this.state.firstLanguage,
      this.state.interpreterRequired,
        activatedDate,
      this.state.profilePhotoUrl,
      this.state.lastRecordedHeight,
      this.state.lastRecordedWeight,
      this.state.hasMedicalCondition,
      this.state.medicalConditionSummary,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserState> onProfilePhotoUrlChange(ProfilePhotoUrlChanged event) async* {
    final profilePhotoUrl = ProfilePhotoUrlInput.dirty(event.profilePhotoUrl);
    yield this.state.copyWith(
      profilePhotoUrl: profilePhotoUrl,
      formStatus: Formz.validate([
      this.state.titlle,
      this.state.firstName,
      this.state.middleName,
      this.state.lastName,
      this.state.preferredName,
      this.state.email,
      this.state.serviceUserCode,
      this.state.dateOfBirth,
      this.state.lastVisitDate,
      this.state.startDate,
      this.state.supportType,
      this.state.serviceUserCategory,
      this.state.vulnerability,
      this.state.servicePriority,
      this.state.source,
      this.state.status,
      this.state.firstLanguage,
      this.state.interpreterRequired,
      this.state.activatedDate,
        profilePhotoUrl,
      this.state.lastRecordedHeight,
      this.state.lastRecordedWeight,
      this.state.hasMedicalCondition,
      this.state.medicalConditionSummary,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserState> onLastRecordedHeightChange(LastRecordedHeightChanged event) async* {
    final lastRecordedHeight = LastRecordedHeightInput.dirty(event.lastRecordedHeight);
    yield this.state.copyWith(
      lastRecordedHeight: lastRecordedHeight,
      formStatus: Formz.validate([
      this.state.titlle,
      this.state.firstName,
      this.state.middleName,
      this.state.lastName,
      this.state.preferredName,
      this.state.email,
      this.state.serviceUserCode,
      this.state.dateOfBirth,
      this.state.lastVisitDate,
      this.state.startDate,
      this.state.supportType,
      this.state.serviceUserCategory,
      this.state.vulnerability,
      this.state.servicePriority,
      this.state.source,
      this.state.status,
      this.state.firstLanguage,
      this.state.interpreterRequired,
      this.state.activatedDate,
      this.state.profilePhotoUrl,
        lastRecordedHeight,
      this.state.lastRecordedWeight,
      this.state.hasMedicalCondition,
      this.state.medicalConditionSummary,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserState> onLastRecordedWeightChange(LastRecordedWeightChanged event) async* {
    final lastRecordedWeight = LastRecordedWeightInput.dirty(event.lastRecordedWeight);
    yield this.state.copyWith(
      lastRecordedWeight: lastRecordedWeight,
      formStatus: Formz.validate([
      this.state.titlle,
      this.state.firstName,
      this.state.middleName,
      this.state.lastName,
      this.state.preferredName,
      this.state.email,
      this.state.serviceUserCode,
      this.state.dateOfBirth,
      this.state.lastVisitDate,
      this.state.startDate,
      this.state.supportType,
      this.state.serviceUserCategory,
      this.state.vulnerability,
      this.state.servicePriority,
      this.state.source,
      this.state.status,
      this.state.firstLanguage,
      this.state.interpreterRequired,
      this.state.activatedDate,
      this.state.profilePhotoUrl,
      this.state.lastRecordedHeight,
        lastRecordedWeight,
      this.state.hasMedicalCondition,
      this.state.medicalConditionSummary,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserState> onHasMedicalConditionChange(HasMedicalConditionChanged event) async* {
    final hasMedicalCondition = HasMedicalConditionInput.dirty(event.hasMedicalCondition);
    yield this.state.copyWith(
      hasMedicalCondition: hasMedicalCondition,
      formStatus: Formz.validate([
      this.state.titlle,
      this.state.firstName,
      this.state.middleName,
      this.state.lastName,
      this.state.preferredName,
      this.state.email,
      this.state.serviceUserCode,
      this.state.dateOfBirth,
      this.state.lastVisitDate,
      this.state.startDate,
      this.state.supportType,
      this.state.serviceUserCategory,
      this.state.vulnerability,
      this.state.servicePriority,
      this.state.source,
      this.state.status,
      this.state.firstLanguage,
      this.state.interpreterRequired,
      this.state.activatedDate,
      this.state.profilePhotoUrl,
      this.state.lastRecordedHeight,
      this.state.lastRecordedWeight,
        hasMedicalCondition,
      this.state.medicalConditionSummary,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserState> onMedicalConditionSummaryChange(MedicalConditionSummaryChanged event) async* {
    final medicalConditionSummary = MedicalConditionSummaryInput.dirty(event.medicalConditionSummary);
    yield this.state.copyWith(
      medicalConditionSummary: medicalConditionSummary,
      formStatus: Formz.validate([
      this.state.titlle,
      this.state.firstName,
      this.state.middleName,
      this.state.lastName,
      this.state.preferredName,
      this.state.email,
      this.state.serviceUserCode,
      this.state.dateOfBirth,
      this.state.lastVisitDate,
      this.state.startDate,
      this.state.supportType,
      this.state.serviceUserCategory,
      this.state.vulnerability,
      this.state.servicePriority,
      this.state.source,
      this.state.status,
      this.state.firstLanguage,
      this.state.interpreterRequired,
      this.state.activatedDate,
      this.state.profilePhotoUrl,
      this.state.lastRecordedHeight,
      this.state.lastRecordedWeight,
      this.state.hasMedicalCondition,
        medicalConditionSummary,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.titlle,
      this.state.firstName,
      this.state.middleName,
      this.state.lastName,
      this.state.preferredName,
      this.state.email,
      this.state.serviceUserCode,
      this.state.dateOfBirth,
      this.state.lastVisitDate,
      this.state.startDate,
      this.state.supportType,
      this.state.serviceUserCategory,
      this.state.vulnerability,
      this.state.servicePriority,
      this.state.source,
      this.state.status,
      this.state.firstLanguage,
      this.state.interpreterRequired,
      this.state.activatedDate,
      this.state.profilePhotoUrl,
      this.state.lastRecordedHeight,
      this.state.lastRecordedWeight,
      this.state.hasMedicalCondition,
      this.state.medicalConditionSummary,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.titlle,
      this.state.firstName,
      this.state.middleName,
      this.state.lastName,
      this.state.preferredName,
      this.state.email,
      this.state.serviceUserCode,
      this.state.dateOfBirth,
      this.state.lastVisitDate,
      this.state.startDate,
      this.state.supportType,
      this.state.serviceUserCategory,
      this.state.vulnerability,
      this.state.servicePriority,
      this.state.source,
      this.state.status,
      this.state.firstLanguage,
      this.state.interpreterRequired,
      this.state.activatedDate,
      this.state.profilePhotoUrl,
      this.state.lastRecordedHeight,
      this.state.lastRecordedWeight,
      this.state.hasMedicalCondition,
      this.state.medicalConditionSummary,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.titlle,
      this.state.firstName,
      this.state.middleName,
      this.state.lastName,
      this.state.preferredName,
      this.state.email,
      this.state.serviceUserCode,
      this.state.dateOfBirth,
      this.state.lastVisitDate,
      this.state.startDate,
      this.state.supportType,
      this.state.serviceUserCategory,
      this.state.vulnerability,
      this.state.servicePriority,
      this.state.source,
      this.state.status,
      this.state.firstLanguage,
      this.state.interpreterRequired,
      this.state.activatedDate,
      this.state.profilePhotoUrl,
      this.state.lastRecordedHeight,
      this.state.lastRecordedWeight,
      this.state.hasMedicalCondition,
      this.state.medicalConditionSummary,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.titlle,
      this.state.firstName,
      this.state.middleName,
      this.state.lastName,
      this.state.preferredName,
      this.state.email,
      this.state.serviceUserCode,
      this.state.dateOfBirth,
      this.state.lastVisitDate,
      this.state.startDate,
      this.state.supportType,
      this.state.serviceUserCategory,
      this.state.vulnerability,
      this.state.servicePriority,
      this.state.source,
      this.state.status,
      this.state.firstLanguage,
      this.state.interpreterRequired,
      this.state.activatedDate,
      this.state.profilePhotoUrl,
      this.state.lastRecordedHeight,
      this.state.lastRecordedWeight,
      this.state.hasMedicalCondition,
      this.state.medicalConditionSummary,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    firstNameController.dispose();
    middleNameController.dispose();
    lastNameController.dispose();
    preferredNameController.dispose();
    emailController.dispose();
    serviceUserCodeController.dispose();
    dateOfBirthController.dispose();
    lastVisitDateController.dispose();
    startDateController.dispose();
    firstLanguageController.dispose();
    activatedDateController.dispose();
    profilePhotoUrlController.dispose();
    lastRecordedHeightController.dispose();
    lastRecordedWeightController.dispose();
    medicalConditionSummaryController.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}