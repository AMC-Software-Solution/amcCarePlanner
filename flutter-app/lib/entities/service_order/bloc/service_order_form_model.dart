import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/service_order/service_order_model.dart';

enum TitleValidationError { invalid }
class TitleInput extends FormzInput<String, TitleValidationError> {
  const TitleInput.pure() : super.pure('');
  const TitleInput.dirty([String value = '']) : super.dirty(value);

  @override
  TitleValidationError validator(String value) {
    return null;
  }
}

enum ServiceDescriptionValidationError { invalid }
class ServiceDescriptionInput extends FormzInput<String, ServiceDescriptionValidationError> {
  const ServiceDescriptionInput.pure() : super.pure('');
  const ServiceDescriptionInput.dirty([String value = '']) : super.dirty(value);

  @override
  ServiceDescriptionValidationError validator(String value) {
    return null;
  }
}

enum ServiceHourlyRateValidationError { invalid }
class ServiceHourlyRateInput extends FormzInput<String, ServiceHourlyRateValidationError> {
  const ServiceHourlyRateInput.pure() : super.pure('');
  const ServiceHourlyRateInput.dirty([String value = '']) : super.dirty(value);

  @override
  ServiceHourlyRateValidationError validator(String value) {
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

