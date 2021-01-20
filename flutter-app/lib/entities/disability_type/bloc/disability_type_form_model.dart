import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/disability_type/disability_type_model.dart';

enum DisabilityValidationError { invalid }
class DisabilityInput extends FormzInput<String, DisabilityValidationError> {
  const DisabilityInput.pure() : super.pure('');
  const DisabilityInput.dirty([String value = '']) : super.dirty(value);

  @override
  DisabilityValidationError validator(String value) {
    return null;
  }
}

enum DescriptionValidationError { invalid }
class DescriptionInput extends FormzInput<String, DescriptionValidationError> {
  const DescriptionInput.pure() : super.pure('');
  const DescriptionInput.dirty([String value = '']) : super.dirty(value);

  @override
  DescriptionValidationError validator(String value) {
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

