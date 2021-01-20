import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/carer_service_user_relation/carer_service_user_relation_model.dart';

enum ReasonValidationError { invalid }
class ReasonInput extends FormzInput<String, ReasonValidationError> {
  const ReasonInput.pure() : super.pure('');
  const ReasonInput.dirty([String value = '']) : super.dirty(value);

  @override
  ReasonValidationError validator(String value) {
    return null;
  }
}

enum CountValidationError { invalid }
class CountInput extends FormzInput<int, CountValidationError> {
  const CountInput.pure() : super.pure(0);
  const CountInput.dirty([int value = 0]) : super.dirty(value);

  @override
  CountValidationError validator(int value) {
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

