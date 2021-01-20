import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/branch/branch_model.dart';

enum NameValidationError { invalid }
class NameInput extends FormzInput<String, NameValidationError> {
  const NameInput.pure() : super.pure('');
  const NameInput.dirty([String value = '']) : super.dirty(value);

  @override
  NameValidationError validator(String value) {
    return null;
  }
}

enum AddressValidationError { invalid }
class AddressInput extends FormzInput<String, AddressValidationError> {
  const AddressInput.pure() : super.pure('');
  const AddressInput.dirty([String value = '']) : super.dirty(value);

  @override
  AddressValidationError validator(String value) {
    return null;
  }
}

enum TelephoneValidationError { invalid }
class TelephoneInput extends FormzInput<String, TelephoneValidationError> {
  const TelephoneInput.pure() : super.pure('');
  const TelephoneInput.dirty([String value = '']) : super.dirty(value);

  @override
  TelephoneValidationError validator(String value) {
    return null;
  }
}

enum ContactNameValidationError { invalid }
class ContactNameInput extends FormzInput<String, ContactNameValidationError> {
  const ContactNameInput.pure() : super.pure('');
  const ContactNameInput.dirty([String value = '']) : super.dirty(value);

  @override
  ContactNameValidationError validator(String value) {
    return null;
  }
}

enum BranchEmailValidationError { invalid }
class BranchEmailInput extends FormzInput<String, BranchEmailValidationError> {
  const BranchEmailInput.pure() : super.pure('');
  const BranchEmailInput.dirty([String value = '']) : super.dirty(value);

  @override
  BranchEmailValidationError validator(String value) {
    return null;
  }
}

enum PhotoUrlValidationError { invalid }
class PhotoUrlInput extends FormzInput<String, PhotoUrlValidationError> {
  const PhotoUrlInput.pure() : super.pure('');
  const PhotoUrlInput.dirty([String value = '']) : super.dirty(value);

  @override
  PhotoUrlValidationError validator(String value) {
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

