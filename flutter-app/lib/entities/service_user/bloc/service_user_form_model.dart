import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/service_user/service_user_model.dart';

enum TitleValidationError { invalid }
class TitleInput extends FormzInput<ServiceUserTitle, TitleValidationError> {
  const TitleInput.pure() : super.pure(ServiceUserTitle.MR);
  const TitleInput.dirty([ServiceUserTitle value]) : super.dirty(value);

  @override
  TitleValidationError validator(ServiceUserTitle value) {
    return null;
  }
}

enum FirstNameValidationError { invalid }
class FirstNameInput extends FormzInput<String, FirstNameValidationError> {
  const FirstNameInput.pure() : super.pure('');
  const FirstNameInput.dirty([String value = '']) : super.dirty(value);

  @override
  FirstNameValidationError validator(String value) {
    return null;
  }
}

enum MiddleNameValidationError { invalid }
class MiddleNameInput extends FormzInput<String, MiddleNameValidationError> {
  const MiddleNameInput.pure() : super.pure('');
  const MiddleNameInput.dirty([String value = '']) : super.dirty(value);

  @override
  MiddleNameValidationError validator(String value) {
    return null;
  }
}

enum LastNameValidationError { invalid }
class LastNameInput extends FormzInput<String, LastNameValidationError> {
  const LastNameInput.pure() : super.pure('');
  const LastNameInput.dirty([String value = '']) : super.dirty(value);

  @override
  LastNameValidationError validator(String value) {
    return null;
  }
}

enum PreferredNameValidationError { invalid }
class PreferredNameInput extends FormzInput<String, PreferredNameValidationError> {
  const PreferredNameInput.pure() : super.pure('');
  const PreferredNameInput.dirty([String value = '']) : super.dirty(value);

  @override
  PreferredNameValidationError validator(String value) {
    return null;
  }
}

enum EmailValidationError { invalid }
class EmailInput extends FormzInput<String, EmailValidationError> {
  const EmailInput.pure() : super.pure('');
  const EmailInput.dirty([String value = '']) : super.dirty(value);

  @override
  EmailValidationError validator(String value) {
    return null;
  }
}

enum ServiceUserCodeValidationError { invalid }
class ServiceUserCodeInput extends FormzInput<String, ServiceUserCodeValidationError> {
  const ServiceUserCodeInput.pure() : super.pure('');
  const ServiceUserCodeInput.dirty([String value = '']) : super.dirty(value);

  @override
  ServiceUserCodeValidationError validator(String value) {
    return null;
  }
}

enum DateOfBirthValidationError { invalid }
class DateOfBirthInput extends FormzInput<DateTime, DateOfBirthValidationError> {
  DateOfBirthInput.pure() : super.pure(DateTime.now());
  DateOfBirthInput.dirty([DateTime value]) : super.dirty(value);

  @override
  DateOfBirthValidationError validator(DateTime value) {
    return null;
  }
}

enum LastVisitDateValidationError { invalid }
class LastVisitDateInput extends FormzInput<DateTime, LastVisitDateValidationError> {
  LastVisitDateInput.pure() : super.pure(DateTime.now());
  LastVisitDateInput.dirty([DateTime value]) : super.dirty(value);

  @override
  LastVisitDateValidationError validator(DateTime value) {
    return null;
  }
}

enum StartDateValidationError { invalid }
class StartDateInput extends FormzInput<DateTime, StartDateValidationError> {
  StartDateInput.pure() : super.pure(DateTime.now());
  StartDateInput.dirty([DateTime value]) : super.dirty(value);

  @override
  StartDateValidationError validator(DateTime value) {
    return null;
  }
}

enum SupportTypeValidationError { invalid }
class SupportTypeInput extends FormzInput<SupportType, SupportTypeValidationError> {
  const SupportTypeInput.pure() : super.pure(SupportType.COMPLEX_CARE_LIVE_IN);
  const SupportTypeInput.dirty([SupportType value]) : super.dirty(value);

  @override
  SupportTypeValidationError validator(SupportType value) {
    return null;
  }
}

enum ServiceUserCategoryValidationError { invalid }
class ServiceUserCategoryInput extends FormzInput<ServiceUserCategory, ServiceUserCategoryValidationError> {
  const ServiceUserCategoryInput.pure() : super.pure(ServiceUserCategory.HIV_AIDS);
  const ServiceUserCategoryInput.dirty([ServiceUserCategory value]) : super.dirty(value);

  @override
  ServiceUserCategoryValidationError validator(ServiceUserCategory value) {
    return null;
  }
}

enum VulnerabilityValidationError { invalid }
class VulnerabilityInput extends FormzInput<Vulnerability, VulnerabilityValidationError> {
  const VulnerabilityInput.pure() : super.pure(Vulnerability.HIV_AIDS);
  const VulnerabilityInput.dirty([Vulnerability value]) : super.dirty(value);

  @override
  VulnerabilityValidationError validator(Vulnerability value) {
    return null;
  }
}

enum ServicePriorityValidationError { invalid }
class ServicePriorityInput extends FormzInput<ServicePriority, ServicePriorityValidationError> {
  const ServicePriorityInput.pure() : super.pure(ServicePriority.HIGH);
  const ServicePriorityInput.dirty([ServicePriority value]) : super.dirty(value);

  @override
  ServicePriorityValidationError validator(ServicePriority value) {
    return null;
  }
}

enum SourceValidationError { invalid }
class SourceInput extends FormzInput<Source, SourceValidationError> {
  const SourceInput.pure() : super.pure(Source.PRIVATE_SERVICE_USER);
  const SourceInput.dirty([Source value]) : super.dirty(value);

  @override
  SourceValidationError validator(Source value) {
    return null;
  }
}

enum StatusValidationError { invalid }
class StatusInput extends FormzInput<ServiceUserStatus, StatusValidationError> {
  const StatusInput.pure() : super.pure(ServiceUserStatus.ACTIVE);
  const StatusInput.dirty([ServiceUserStatus value]) : super.dirty(value);

  @override
  StatusValidationError validator(ServiceUserStatus value) {
    return null;
  }
}

enum FirstLanguageValidationError { invalid }
class FirstLanguageInput extends FormzInput<String, FirstLanguageValidationError> {
  const FirstLanguageInput.pure() : super.pure('');
  const FirstLanguageInput.dirty([String value = '']) : super.dirty(value);

  @override
  FirstLanguageValidationError validator(String value) {
    return null;
  }
}

enum InterpreterRequiredValidationError { invalid }
class InterpreterRequiredInput extends FormzInput<bool, InterpreterRequiredValidationError> {
  const InterpreterRequiredInput.pure() : super.pure(false);
  const InterpreterRequiredInput.dirty([bool value = false]) : super.dirty(value);

  @override
  InterpreterRequiredValidationError validator(bool value) {
    return null;
  }
}

enum ActivatedDateValidationError { invalid }
class ActivatedDateInput extends FormzInput<DateTime, ActivatedDateValidationError> {
  ActivatedDateInput.pure() : super.pure(DateTime.now());
  ActivatedDateInput.dirty([DateTime value]) : super.dirty(value);

  @override
  ActivatedDateValidationError validator(DateTime value) {
    return null;
  }
}

enum ProfilePhotoUrlValidationError { invalid }
class ProfilePhotoUrlInput extends FormzInput<String, ProfilePhotoUrlValidationError> {
  const ProfilePhotoUrlInput.pure() : super.pure('');
  const ProfilePhotoUrlInput.dirty([String value = '']) : super.dirty(value);

  @override
  ProfilePhotoUrlValidationError validator(String value) {
    return null;
  }
}

enum LastRecordedHeightValidationError { invalid }
class LastRecordedHeightInput extends FormzInput<String, LastRecordedHeightValidationError> {
  const LastRecordedHeightInput.pure() : super.pure('');
  const LastRecordedHeightInput.dirty([String value = '']) : super.dirty(value);

  @override
  LastRecordedHeightValidationError validator(String value) {
    return null;
  }
}

enum LastRecordedWeightValidationError { invalid }
class LastRecordedWeightInput extends FormzInput<String, LastRecordedWeightValidationError> {
  const LastRecordedWeightInput.pure() : super.pure('');
  const LastRecordedWeightInput.dirty([String value = '']) : super.dirty(value);

  @override
  LastRecordedWeightValidationError validator(String value) {
    return null;
  }
}

enum HasMedicalConditionValidationError { invalid }
class HasMedicalConditionInput extends FormzInput<bool, HasMedicalConditionValidationError> {
  const HasMedicalConditionInput.pure() : super.pure(false);
  const HasMedicalConditionInput.dirty([bool value = false]) : super.dirty(value);

  @override
  HasMedicalConditionValidationError validator(bool value) {
    return null;
  }
}

enum MedicalConditionSummaryValidationError { invalid }
class MedicalConditionSummaryInput extends FormzInput<String, MedicalConditionSummaryValidationError> {
  const MedicalConditionSummaryInput.pure() : super.pure('');
  const MedicalConditionSummaryInput.dirty([String value = '']) : super.dirty(value);

  @override
  MedicalConditionSummaryValidationError validator(String value) {
    return null;
  }
}

enum CreatedDateValidationError { invalid }
class CreatedDateInput extends FormzInput<DateTime, CreatedDateValidationError> {
  CreatedDateInput.pure() : super.pure(DateTime.now());
  CreatedDateInput.dirty([DateTime value]) : super.dirty(value);

  @override
  CreatedDateValidationError validator(DateTime value) {
    return null;
  }
}

enum LastUpdatedDateValidationError { invalid }
class LastUpdatedDateInput extends FormzInput<DateTime, LastUpdatedDateValidationError> {
  LastUpdatedDateInput.pure() : super.pure(DateTime.now());
  LastUpdatedDateInput.dirty([DateTime value]) : super.dirty(value);

  @override
  LastUpdatedDateValidationError validator(DateTime value) {
    return null;
  }
}

enum ClientIdValidationError { invalid }
class ClientIdInput extends FormzInput<int, ClientIdValidationError> {
  const ClientIdInput.pure() : super.pure(0);
  const ClientIdInput.dirty([int value = 0]) : super.dirty(value);

  @override
  ClientIdValidationError validator(int value) {
    return null;
  }
}

enum HasExtraDataValidationError { invalid }
class HasExtraDataInput extends FormzInput<bool, HasExtraDataValidationError> {
  const HasExtraDataInput.pure() : super.pure(false);
  const HasExtraDataInput.dirty([bool value = false]) : super.dirty(value);

  @override
  HasExtraDataValidationError validator(bool value) {
    return null;
  }
}

