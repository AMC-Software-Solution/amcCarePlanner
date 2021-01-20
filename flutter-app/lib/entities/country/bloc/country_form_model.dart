import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/country/country_model.dart';

enum CountryNameValidationError { invalid }
class CountryNameInput extends FormzInput<String, CountryNameValidationError> {
  const CountryNameInput.pure() : super.pure('');
  const CountryNameInput.dirty([String value = '']) : super.dirty(value);

  @override
  CountryNameValidationError validator(String value) {
    return null;
  }
}

enum CountryIsoCodeValidationError { invalid }
class CountryIsoCodeInput extends FormzInput<String, CountryIsoCodeValidationError> {
  const CountryIsoCodeInput.pure() : super.pure('');
  const CountryIsoCodeInput.dirty([String value = '']) : super.dirty(value);

  @override
  CountryIsoCodeValidationError validator(String value) {
    return null;
  }
}

enum CountryFlagUrlValidationError { invalid }
class CountryFlagUrlInput extends FormzInput<String, CountryFlagUrlValidationError> {
  const CountryFlagUrlInput.pure() : super.pure('');
  const CountryFlagUrlInput.dirty([String value = '']) : super.dirty(value);

  @override
  CountryFlagUrlValidationError validator(String value) {
    return null;
  }
}

enum CountryCallingCodeValidationError { invalid }
class CountryCallingCodeInput extends FormzInput<String, CountryCallingCodeValidationError> {
  const CountryCallingCodeInput.pure() : super.pure('');
  const CountryCallingCodeInput.dirty([String value = '']) : super.dirty(value);

  @override
  CountryCallingCodeValidationError validator(String value) {
    return null;
  }
}

enum CountryTelDigitLengthValidationError { invalid }
class CountryTelDigitLengthInput extends FormzInput<int, CountryTelDigitLengthValidationError> {
  const CountryTelDigitLengthInput.pure() : super.pure(0);
  const CountryTelDigitLengthInput.dirty([int value = 0]) : super.dirty(value);

  @override
  CountryTelDigitLengthValidationError validator(int value) {
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

enum HasExtraDataValidationError { invalid }
class HasExtraDataInput extends FormzInput<bool, HasExtraDataValidationError> {
  const HasExtraDataInput.pure() : super.pure(false);
  const HasExtraDataInput.dirty([bool value = false]) : super.dirty(value);

  @override
  HasExtraDataValidationError validator(bool value) {
    return null;
  }
}

