import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/service_user_contact/service_user_contact_model.dart';

enum AddressValidationError { invalid }
class AddressInput extends FormzInput<String, AddressValidationError> {
  const AddressInput.pure() : super.pure('');
  const AddressInput.dirty([String value = '']) : super.dirty(value);

  @override
  AddressValidationError validator(String value) {
    return null;
  }
}

enum CityOrTownValidationError { invalid }
class CityOrTownInput extends FormzInput<String, CityOrTownValidationError> {
  const CityOrTownInput.pure() : super.pure('');
  const CityOrTownInput.dirty([String value = '']) : super.dirty(value);

  @override
  CityOrTownValidationError validator(String value) {
    return null;
  }
}

enum CountyValidationError { invalid }
class CountyInput extends FormzInput<String, CountyValidationError> {
  const CountyInput.pure() : super.pure('');
  const CountyInput.dirty([String value = '']) : super.dirty(value);

  @override
  CountyValidationError validator(String value) {
    return null;
  }
}

enum PostCodeValidationError { invalid }
class PostCodeInput extends FormzInput<String, PostCodeValidationError> {
  const PostCodeInput.pure() : super.pure('');
  const PostCodeInput.dirty([String value = '']) : super.dirty(value);

  @override
  PostCodeValidationError validator(String value) {
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

