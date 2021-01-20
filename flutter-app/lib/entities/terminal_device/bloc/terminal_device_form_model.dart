import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/terminal_device/terminal_device_model.dart';

enum DeviceNameValidationError { invalid }
class DeviceNameInput extends FormzInput<String, DeviceNameValidationError> {
  const DeviceNameInput.pure() : super.pure('');
  const DeviceNameInput.dirty([String value = '']) : super.dirty(value);

  @override
  DeviceNameValidationError validator(String value) {
    return null;
  }
}

enum DeviceModelValidationError { invalid }
class DeviceModelInput extends FormzInput<String, DeviceModelValidationError> {
  const DeviceModelInput.pure() : super.pure('');
  const DeviceModelInput.dirty([String value = '']) : super.dirty(value);

  @override
  DeviceModelValidationError validator(String value) {
    return null;
  }
}

enum RegisteredDateValidationError { invalid }
class RegisteredDateInput extends FormzInput<DateTime, RegisteredDateValidationError> {
  RegisteredDateInput.pure() : super.pure(DateTime.now());
  RegisteredDateInput.dirty([DateTime value]) : super.dirty(value);

  @override
  RegisteredDateValidationError validator(DateTime value) {
    return null;
  }
}

enum ImeiValidationError { invalid }
class ImeiInput extends FormzInput<String, ImeiValidationError> {
  const ImeiInput.pure() : super.pure('');
  const ImeiInput.dirty([String value = '']) : super.dirty(value);

  @override
  ImeiValidationError validator(String value) {
    return null;
  }
}

enum SimNumberValidationError { invalid }
class SimNumberInput extends FormzInput<String, SimNumberValidationError> {
  const SimNumberInput.pure() : super.pure('');
  const SimNumberInput.dirty([String value = '']) : super.dirty(value);

  @override
  SimNumberValidationError validator(String value) {
    return null;
  }
}

enum UserStartedUsingFromValidationError { invalid }
class UserStartedUsingFromInput extends FormzInput<DateTime, UserStartedUsingFromValidationError> {
  UserStartedUsingFromInput.pure() : super.pure(DateTime.now());
  UserStartedUsingFromInput.dirty([DateTime value]) : super.dirty(value);

  @override
  UserStartedUsingFromValidationError validator(DateTime value) {
    return null;
  }
}

enum DeviceOnLocationFromValidationError { invalid }
class DeviceOnLocationFromInput extends FormzInput<DateTime, DeviceOnLocationFromValidationError> {
  DeviceOnLocationFromInput.pure() : super.pure(DateTime.now());
  DeviceOnLocationFromInput.dirty([DateTime value]) : super.dirty(value);

  @override
  DeviceOnLocationFromValidationError validator(DateTime value) {
    return null;
  }
}

enum OperatingSystemValidationError { invalid }
class OperatingSystemInput extends FormzInput<String, OperatingSystemValidationError> {
  const OperatingSystemInput.pure() : super.pure('');
  const OperatingSystemInput.dirty([String value = '']) : super.dirty(value);

  @override
  OperatingSystemValidationError validator(String value) {
    return null;
  }
}

enum NoteValidationError { invalid }
class NoteInput extends FormzInput<String, NoteValidationError> {
  const NoteInput.pure() : super.pure('');
  const NoteInput.dirty([String value = '']) : super.dirty(value);

  @override
  NoteValidationError validator(String value) {
    return null;
  }
}

enum OwnerEntityIdValidationError { invalid }
class OwnerEntityIdInput extends FormzInput<int, OwnerEntityIdValidationError> {
  const OwnerEntityIdInput.pure() : super.pure(0);
  const OwnerEntityIdInput.dirty([int value = 0]) : super.dirty(value);

  @override
  OwnerEntityIdValidationError validator(int value) {
    return null;
  }
}

enum OwnerEntityNameValidationError { invalid }
class OwnerEntityNameInput extends FormzInput<String, OwnerEntityNameValidationError> {
  const OwnerEntityNameInput.pure() : super.pure('');
  const OwnerEntityNameInput.dirty([String value = '']) : super.dirty(value);

  @override
  OwnerEntityNameValidationError validator(String value) {
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

