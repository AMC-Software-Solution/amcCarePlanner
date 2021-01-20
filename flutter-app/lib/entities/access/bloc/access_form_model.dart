import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/access/access_model.dart';

enum KeySafeNumberValidationError { invalid }
class KeySafeNumberInput extends FormzInput<String, KeySafeNumberValidationError> {
  const KeySafeNumberInput.pure() : super.pure('');
  const KeySafeNumberInput.dirty([String value = '']) : super.dirty(value);

  @override
  KeySafeNumberValidationError validator(String value) {
    return null;
  }
}

enum AccessDetailsValidationError { invalid }
class AccessDetailsInput extends FormzInput<String, AccessDetailsValidationError> {
  const AccessDetailsInput.pure() : super.pure('');
  const AccessDetailsInput.dirty([String value = '']) : super.dirty(value);

  @override
  AccessDetailsValidationError validator(String value) {
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

