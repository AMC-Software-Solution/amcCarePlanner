import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/currency/currency_model.dart';

enum CurrencyValidationError { invalid }
class CurrencyInput extends FormzInput<String, CurrencyValidationError> {
  const CurrencyInput.pure() : super.pure('');
  const CurrencyInput.dirty([String value = '']) : super.dirty(value);

  @override
  CurrencyValidationError validator(String value) {
    return null;
  }
}

enum CurrencyIsoCodeValidationError { invalid }
class CurrencyIsoCodeInput extends FormzInput<String, CurrencyIsoCodeValidationError> {
  const CurrencyIsoCodeInput.pure() : super.pure('');
  const CurrencyIsoCodeInput.dirty([String value = '']) : super.dirty(value);

  @override
  CurrencyIsoCodeValidationError validator(String value) {
    return null;
  }
}

enum CurrencySymbolValidationError { invalid }
class CurrencySymbolInput extends FormzInput<String, CurrencySymbolValidationError> {
  const CurrencySymbolInput.pure() : super.pure('');
  const CurrencySymbolInput.dirty([String value = '']) : super.dirty(value);

  @override
  CurrencySymbolValidationError validator(String value) {
    return null;
  }
}

enum CurrencyLogoUrlValidationError { invalid }
class CurrencyLogoUrlInput extends FormzInput<String, CurrencyLogoUrlValidationError> {
  const CurrencyLogoUrlInput.pure() : super.pure('');
  const CurrencyLogoUrlInput.dirty([String value = '']) : super.dirty(value);

  @override
  CurrencyLogoUrlValidationError validator(String value) {
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

