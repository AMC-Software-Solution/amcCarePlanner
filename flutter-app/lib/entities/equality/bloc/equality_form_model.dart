import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/equality/equality_model.dart';

enum GenderValidationError { invalid }
class GenderInput extends FormzInput<ServiceUserGender, GenderValidationError> {
  const GenderInput.pure() : super.pure(ServiceUserGender.MALE);
  const GenderInput.dirty([ServiceUserGender value]) : super.dirty(value);

  @override
  GenderValidationError validator(ServiceUserGender value) {
    return null;
  }
}

enum MaritalStatusValidationError { invalid }
class MaritalStatusInput extends FormzInput<MaritalStatus, MaritalStatusValidationError> {
  const MaritalStatusInput.pure() : super.pure(MaritalStatus.MARRIED);
  const MaritalStatusInput.dirty([MaritalStatus value]) : super.dirty(value);

  @override
  MaritalStatusValidationError validator(MaritalStatus value) {
    return null;
  }
}

enum ReligionValidationError { invalid }
class ReligionInput extends FormzInput<Religion, ReligionValidationError> {
  const ReligionInput.pure() : super.pure(Religion.MUSLIM);
  const ReligionInput.dirty([Religion value]) : super.dirty(value);

  @override
  ReligionValidationError validator(Religion value) {
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

