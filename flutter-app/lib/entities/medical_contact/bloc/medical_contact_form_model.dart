import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/medical_contact/medical_contact_model.dart';

enum DoctorNameValidationError { invalid }
class DoctorNameInput extends FormzInput<String, DoctorNameValidationError> {
  const DoctorNameInput.pure() : super.pure('');
  const DoctorNameInput.dirty([String value = '']) : super.dirty(value);

  @override
  DoctorNameValidationError validator(String value) {
    return null;
  }
}

enum DoctorSurgeryValidationError { invalid }
class DoctorSurgeryInput extends FormzInput<String, DoctorSurgeryValidationError> {
  const DoctorSurgeryInput.pure() : super.pure('');
  const DoctorSurgeryInput.dirty([String value = '']) : super.dirty(value);

  @override
  DoctorSurgeryValidationError validator(String value) {
    return null;
  }
}

enum DoctorAddressValidationError { invalid }
class DoctorAddressInput extends FormzInput<String, DoctorAddressValidationError> {
  const DoctorAddressInput.pure() : super.pure('');
  const DoctorAddressInput.dirty([String value = '']) : super.dirty(value);

  @override
  DoctorAddressValidationError validator(String value) {
    return null;
  }
}

enum DoctorPhoneValidationError { invalid }
class DoctorPhoneInput extends FormzInput<String, DoctorPhoneValidationError> {
  const DoctorPhoneInput.pure() : super.pure('');
  const DoctorPhoneInput.dirty([String value = '']) : super.dirty(value);

  @override
  DoctorPhoneValidationError validator(String value) {
    return null;
  }
}

enum LastVisitedDoctorValidationError { invalid }
class LastVisitedDoctorInput extends FormzInput<DateTime, LastVisitedDoctorValidationError> {
  LastVisitedDoctorInput.pure() : super.pure(DateTime.now());
  LastVisitedDoctorInput.dirty([DateTime value]) : super.dirty(value);

  @override
  LastVisitedDoctorValidationError validator(DateTime value) {
    return null;
  }
}

enum DistrictNurseNameValidationError { invalid }
class DistrictNurseNameInput extends FormzInput<String, DistrictNurseNameValidationError> {
  const DistrictNurseNameInput.pure() : super.pure('');
  const DistrictNurseNameInput.dirty([String value = '']) : super.dirty(value);

  @override
  DistrictNurseNameValidationError validator(String value) {
    return null;
  }
}

enum DistrictNursePhoneValidationError { invalid }
class DistrictNursePhoneInput extends FormzInput<String, DistrictNursePhoneValidationError> {
  const DistrictNursePhoneInput.pure() : super.pure('');
  const DistrictNursePhoneInput.dirty([String value = '']) : super.dirty(value);

  @override
  DistrictNursePhoneValidationError validator(String value) {
    return null;
  }
}

enum CareManagerNameValidationError { invalid }
class CareManagerNameInput extends FormzInput<String, CareManagerNameValidationError> {
  const CareManagerNameInput.pure() : super.pure('');
  const CareManagerNameInput.dirty([String value = '']) : super.dirty(value);

  @override
  CareManagerNameValidationError validator(String value) {
    return null;
  }
}

enum CareManagerPhoneValidationError { invalid }
class CareManagerPhoneInput extends FormzInput<String, CareManagerPhoneValidationError> {
  const CareManagerPhoneInput.pure() : super.pure('');
  const CareManagerPhoneInput.dirty([String value = '']) : super.dirty(value);

  @override
  CareManagerPhoneValidationError validator(String value) {
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

