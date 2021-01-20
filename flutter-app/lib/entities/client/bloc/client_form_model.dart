import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/client/client_model.dart';

enum ClientNameValidationError { invalid }
class ClientNameInput extends FormzInput<String, ClientNameValidationError> {
  const ClientNameInput.pure() : super.pure('');
  const ClientNameInput.dirty([String value = '']) : super.dirty(value);

  @override
  ClientNameValidationError validator(String value) {
    return null;
  }
}

enum ClientDescriptionValidationError { invalid }
class ClientDescriptionInput extends FormzInput<String, ClientDescriptionValidationError> {
  const ClientDescriptionInput.pure() : super.pure('');
  const ClientDescriptionInput.dirty([String value = '']) : super.dirty(value);

  @override
  ClientDescriptionValidationError validator(String value) {
    return null;
  }
}

enum ClientLogoUrlValidationError { invalid }
class ClientLogoUrlInput extends FormzInput<String, ClientLogoUrlValidationError> {
  const ClientLogoUrlInput.pure() : super.pure('');
  const ClientLogoUrlInput.dirty([String value = '']) : super.dirty(value);

  @override
  ClientLogoUrlValidationError validator(String value) {
    return null;
  }
}

enum ClientContactNameValidationError { invalid }
class ClientContactNameInput extends FormzInput<String, ClientContactNameValidationError> {
  const ClientContactNameInput.pure() : super.pure('');
  const ClientContactNameInput.dirty([String value = '']) : super.dirty(value);

  @override
  ClientContactNameValidationError validator(String value) {
    return null;
  }
}

enum ClientPhoneValidationError { invalid }
class ClientPhoneInput extends FormzInput<String, ClientPhoneValidationError> {
  const ClientPhoneInput.pure() : super.pure('');
  const ClientPhoneInput.dirty([String value = '']) : super.dirty(value);

  @override
  ClientPhoneValidationError validator(String value) {
    return null;
  }
}

enum ClientEmailValidationError { invalid }
class ClientEmailInput extends FormzInput<String, ClientEmailValidationError> {
  const ClientEmailInput.pure() : super.pure('');
  const ClientEmailInput.dirty([String value = '']) : super.dirty(value);

  @override
  ClientEmailValidationError validator(String value) {
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

enum EnabledValidationError { invalid }
class EnabledInput extends FormzInput<bool, EnabledValidationError> {
  const EnabledInput.pure() : super.pure(false);
  const EnabledInput.dirty([bool value = false]) : super.dirty(value);

  @override
  EnabledValidationError validator(bool value) {
    return null;
  }
}

enum ReasonValidationError { invalid }
class ReasonInput extends FormzInput<String, ReasonValidationError> {
  const ReasonInput.pure() : super.pure('');
  const ReasonInput.dirty([String value = '']) : super.dirty(value);

  @override
  ReasonValidationError validator(String value) {
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

