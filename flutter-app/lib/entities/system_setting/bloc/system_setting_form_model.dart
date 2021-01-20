import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/system_setting/system_setting_model.dart';

enum FieldNameValidationError { invalid }
class FieldNameInput extends FormzInput<String, FieldNameValidationError> {
  const FieldNameInput.pure() : super.pure('');
  const FieldNameInput.dirty([String value = '']) : super.dirty(value);

  @override
  FieldNameValidationError validator(String value) {
    return null;
  }
}

enum FieldValueValidationError { invalid }
class FieldValueInput extends FormzInput<String, FieldValueValidationError> {
  const FieldValueInput.pure() : super.pure('');
  const FieldValueInput.dirty([String value = '']) : super.dirty(value);

  @override
  FieldValueValidationError validator(String value) {
    return null;
  }
}

enum DefaultValueValidationError { invalid }
class DefaultValueInput extends FormzInput<String, DefaultValueValidationError> {
  const DefaultValueInput.pure() : super.pure('');
  const DefaultValueInput.dirty([String value = '']) : super.dirty(value);

  @override
  DefaultValueValidationError validator(String value) {
    return null;
  }
}

enum SettingEnabledValidationError { invalid }
class SettingEnabledInput extends FormzInput<bool, SettingEnabledValidationError> {
  const SettingEnabledInput.pure() : super.pure(false);
  const SettingEnabledInput.dirty([bool value = false]) : super.dirty(value);

  @override
  SettingEnabledValidationError validator(bool value) {
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

enum UpdatedDateValidationError { invalid }
class UpdatedDateInput extends FormzInput<DateTime, UpdatedDateValidationError> {
  UpdatedDateInput.pure() : super.pure(DateTime.now());
  UpdatedDateInput.dirty([DateTime value]) : super.dirty(value);

  @override
  UpdatedDateValidationError validator(DateTime value) {
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

