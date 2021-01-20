import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/power_of_attorney/power_of_attorney_model.dart';

enum PowerOfAttorneyConsentValidationError { invalid }
class PowerOfAttorneyConsentInput extends FormzInput<bool, PowerOfAttorneyConsentValidationError> {
  const PowerOfAttorneyConsentInput.pure() : super.pure(false);
  const PowerOfAttorneyConsentInput.dirty([bool value = false]) : super.dirty(value);

  @override
  PowerOfAttorneyConsentValidationError validator(bool value) {
    return null;
  }
}

enum HealthAndWelfareValidationError { invalid }
class HealthAndWelfareInput extends FormzInput<bool, HealthAndWelfareValidationError> {
  const HealthAndWelfareInput.pure() : super.pure(false);
  const HealthAndWelfareInput.dirty([bool value = false]) : super.dirty(value);

  @override
  HealthAndWelfareValidationError validator(bool value) {
    return null;
  }
}

enum HealthAndWelfareNameValidationError { invalid }
class HealthAndWelfareNameInput extends FormzInput<String, HealthAndWelfareNameValidationError> {
  const HealthAndWelfareNameInput.pure() : super.pure('');
  const HealthAndWelfareNameInput.dirty([String value = '']) : super.dirty(value);

  @override
  HealthAndWelfareNameValidationError validator(String value) {
    return null;
  }
}

enum PropertyAndFinAffairsValidationError { invalid }
class PropertyAndFinAffairsInput extends FormzInput<bool, PropertyAndFinAffairsValidationError> {
  const PropertyAndFinAffairsInput.pure() : super.pure(false);
  const PropertyAndFinAffairsInput.dirty([bool value = false]) : super.dirty(value);

  @override
  PropertyAndFinAffairsValidationError validator(bool value) {
    return null;
  }
}

enum PropertyAndFinAffairsNameValidationError { invalid }
class PropertyAndFinAffairsNameInput extends FormzInput<String, PropertyAndFinAffairsNameValidationError> {
  const PropertyAndFinAffairsNameInput.pure() : super.pure('');
  const PropertyAndFinAffairsNameInput.dirty([String value = '']) : super.dirty(value);

  @override
  PropertyAndFinAffairsNameValidationError validator(String value) {
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

