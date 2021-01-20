import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/emergency_contact/emergency_contact_model.dart';

enum NameValidationError { invalid }
class NameInput extends FormzInput<String, NameValidationError> {
  const NameInput.pure() : super.pure('');
  const NameInput.dirty([String value = '']) : super.dirty(value);

  @override
  NameValidationError validator(String value) {
    return null;
  }
}

enum ContactRelationshipValidationError { invalid }
class ContactRelationshipInput extends FormzInput<String, ContactRelationshipValidationError> {
  const ContactRelationshipInput.pure() : super.pure('');
  const ContactRelationshipInput.dirty([String value = '']) : super.dirty(value);

  @override
  ContactRelationshipValidationError validator(String value) {
    return null;
  }
}

enum IsKeyHolderValidationError { invalid }
class IsKeyHolderInput extends FormzInput<bool, IsKeyHolderValidationError> {
  const IsKeyHolderInput.pure() : super.pure(false);
  const IsKeyHolderInput.dirty([bool value = false]) : super.dirty(value);

  @override
  IsKeyHolderValidationError validator(bool value) {
    return null;
  }
}

enum InfoSharingConsentGivenValidationError { invalid }
class InfoSharingConsentGivenInput extends FormzInput<bool, InfoSharingConsentGivenValidationError> {
  const InfoSharingConsentGivenInput.pure() : super.pure(false);
  const InfoSharingConsentGivenInput.dirty([bool value = false]) : super.dirty(value);

  @override
  InfoSharingConsentGivenValidationError validator(bool value) {
    return null;
  }
}

enum PreferredContactNumberValidationError { invalid }
class PreferredContactNumberInput extends FormzInput<String, PreferredContactNumberValidationError> {
  const PreferredContactNumberInput.pure() : super.pure('');
  const PreferredContactNumberInput.dirty([String value = '']) : super.dirty(value);

  @override
  PreferredContactNumberValidationError validator(String value) {
    return null;
  }
}

enum FullAddressValidationError { invalid }
class FullAddressInput extends FormzInput<String, FullAddressValidationError> {
  const FullAddressInput.pure() : super.pure('');
  const FullAddressInput.dirty([String value = '']) : super.dirty(value);

  @override
  FullAddressValidationError validator(String value) {
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

