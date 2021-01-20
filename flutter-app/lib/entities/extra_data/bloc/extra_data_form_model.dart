import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/extra_data/extra_data_model.dart';

enum EntityNameValidationError { invalid }
class EntityNameInput extends FormzInput<String, EntityNameValidationError> {
  const EntityNameInput.pure() : super.pure('');
  const EntityNameInput.dirty([String value = '']) : super.dirty(value);

  @override
  EntityNameValidationError validator(String value) {
    return null;
  }
}

enum EntityIdValidationError { invalid }
class EntityIdInput extends FormzInput<int, EntityIdValidationError> {
  const EntityIdInput.pure() : super.pure(0);
  const EntityIdInput.dirty([int value = 0]) : super.dirty(value);

  @override
  EntityIdValidationError validator(int value) {
    return null;
  }
}

enum ExtraDataKeyValidationError { invalid }
class ExtraDataKeyInput extends FormzInput<String, ExtraDataKeyValidationError> {
  const ExtraDataKeyInput.pure() : super.pure('');
  const ExtraDataKeyInput.dirty([String value = '']) : super.dirty(value);

  @override
  ExtraDataKeyValidationError validator(String value) {
    return null;
  }
}

enum ExtraDataValueValidationError { invalid }
class ExtraDataValueInput extends FormzInput<String, ExtraDataValueValidationError> {
  const ExtraDataValueInput.pure() : super.pure('');
  const ExtraDataValueInput.dirty([String value = '']) : super.dirty(value);

  @override
  ExtraDataValueValidationError validator(String value) {
    return null;
  }
}

enum ExtraDataValueDataTypeValidationError { invalid }
class ExtraDataValueDataTypeInput extends FormzInput<DataType, ExtraDataValueDataTypeValidationError> {
  const ExtraDataValueDataTypeInput.pure() : super.pure(DataType.STRING);
  const ExtraDataValueDataTypeInput.dirty([DataType value]) : super.dirty(value);

  @override
  ExtraDataValueDataTypeValidationError validator(DataType value) {
    return null;
  }
}

enum ExtraDataDescriptionValidationError { invalid }
class ExtraDataDescriptionInput extends FormzInput<String, ExtraDataDescriptionValidationError> {
  const ExtraDataDescriptionInput.pure() : super.pure('');
  const ExtraDataDescriptionInput.dirty([String value = '']) : super.dirty(value);

  @override
  ExtraDataDescriptionValidationError validator(String value) {
    return null;
  }
}

enum ExtraDataDateValidationError { invalid }
class ExtraDataDateInput extends FormzInput<DateTime, ExtraDataDateValidationError> {
  ExtraDataDateInput.pure() : super.pure(DateTime.now());
  ExtraDataDateInput.dirty([DateTime value]) : super.dirty(value);

  @override
  ExtraDataDateValidationError validator(DateTime value) {
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

