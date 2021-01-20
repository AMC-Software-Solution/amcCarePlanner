import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/service_user_location/service_user_location_model.dart';

enum LatitudeValidationError { invalid }
class LatitudeInput extends FormzInput<String, LatitudeValidationError> {
  const LatitudeInput.pure() : super.pure('');
  const LatitudeInput.dirty([String value = '']) : super.dirty(value);

  @override
  LatitudeValidationError validator(String value) {
    return null;
  }
}

enum LongitudeValidationError { invalid }
class LongitudeInput extends FormzInput<String, LongitudeValidationError> {
  const LongitudeInput.pure() : super.pure('');
  const LongitudeInput.dirty([String value = '']) : super.dirty(value);

  @override
  LongitudeValidationError validator(String value) {
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

