import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/eligibility_type/eligibility_type_model.dart';

enum EligibilityTypeValidationError { invalid }
class EligibilityTypeInput extends FormzInput<String, EligibilityTypeValidationError> {
  const EligibilityTypeInput.pure() : super.pure('');
  const EligibilityTypeInput.dirty([String value = '']) : super.dirty(value);

  @override
  EligibilityTypeValidationError validator(String value) {
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

