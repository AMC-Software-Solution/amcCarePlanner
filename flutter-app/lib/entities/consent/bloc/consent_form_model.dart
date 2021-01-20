import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/consent/consent_model.dart';

enum TitleValidationError { invalid }
class TitleInput extends FormzInput<String, TitleValidationError> {
  const TitleInput.pure() : super.pure('');
  const TitleInput.dirty([String value = '']) : super.dirty(value);

  @override
  TitleValidationError validator(String value) {
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

enum GiveConsentValidationError { invalid }
class GiveConsentInput extends FormzInput<bool, GiveConsentValidationError> {
  const GiveConsentInput.pure() : super.pure(false);
  const GiveConsentInput.dirty([bool value = false]) : super.dirty(value);

  @override
  GiveConsentValidationError validator(bool value) {
    return null;
  }
}

enum ArrangementsValidationError { invalid }
class ArrangementsInput extends FormzInput<String, ArrangementsValidationError> {
  const ArrangementsInput.pure() : super.pure('');
  const ArrangementsInput.dirty([String value = '']) : super.dirty(value);

  @override
  ArrangementsValidationError validator(String value) {
    return null;
  }
}

enum ServiceUserSignatureValidationError { invalid }
class ServiceUserSignatureInput extends FormzInput<String, ServiceUserSignatureValidationError> {
  const ServiceUserSignatureInput.pure() : super.pure('');
  const ServiceUserSignatureInput.dirty([String value = '']) : super.dirty(value);

  @override
  ServiceUserSignatureValidationError validator(String value) {
    return null;
  }
}

enum SignatureImageUrlValidationError { invalid }
class SignatureImageUrlInput extends FormzInput<String, SignatureImageUrlValidationError> {
  const SignatureImageUrlInput.pure() : super.pure('');
  const SignatureImageUrlInput.dirty([String value = '']) : super.dirty(value);

  @override
  SignatureImageUrlValidationError validator(String value) {
    return null;
  }
}

enum ConsentDateValidationError { invalid }
class ConsentDateInput extends FormzInput<DateTime, ConsentDateValidationError> {
  ConsentDateInput.pure() : super.pure(DateTime.now());
  ConsentDateInput.dirty([DateTime value]) : super.dirty(value);

  @override
  ConsentDateValidationError validator(DateTime value) {
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

