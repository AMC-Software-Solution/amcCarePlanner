import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/employee_availability/employee_availability_model.dart';

enum AvailableForWorkValidationError { invalid }
class AvailableForWorkInput extends FormzInput<bool, AvailableForWorkValidationError> {
  const AvailableForWorkInput.pure() : super.pure(false);
  const AvailableForWorkInput.dirty([bool value = false]) : super.dirty(value);

  @override
  AvailableForWorkValidationError validator(bool value) {
    return null;
  }
}

enum AvailableMondayValidationError { invalid }
class AvailableMondayInput extends FormzInput<bool, AvailableMondayValidationError> {
  const AvailableMondayInput.pure() : super.pure(false);
  const AvailableMondayInput.dirty([bool value = false]) : super.dirty(value);

  @override
  AvailableMondayValidationError validator(bool value) {
    return null;
  }
}

enum AvailableTuesdayValidationError { invalid }
class AvailableTuesdayInput extends FormzInput<bool, AvailableTuesdayValidationError> {
  const AvailableTuesdayInput.pure() : super.pure(false);
  const AvailableTuesdayInput.dirty([bool value = false]) : super.dirty(value);

  @override
  AvailableTuesdayValidationError validator(bool value) {
    return null;
  }
}

enum AvailableWednesdayValidationError { invalid }
class AvailableWednesdayInput extends FormzInput<bool, AvailableWednesdayValidationError> {
  const AvailableWednesdayInput.pure() : super.pure(false);
  const AvailableWednesdayInput.dirty([bool value = false]) : super.dirty(value);

  @override
  AvailableWednesdayValidationError validator(bool value) {
    return null;
  }
}

enum AvailableThursdayValidationError { invalid }
class AvailableThursdayInput extends FormzInput<bool, AvailableThursdayValidationError> {
  const AvailableThursdayInput.pure() : super.pure(false);
  const AvailableThursdayInput.dirty([bool value = false]) : super.dirty(value);

  @override
  AvailableThursdayValidationError validator(bool value) {
    return null;
  }
}

enum AvailableFridayValidationError { invalid }
class AvailableFridayInput extends FormzInput<bool, AvailableFridayValidationError> {
  const AvailableFridayInput.pure() : super.pure(false);
  const AvailableFridayInput.dirty([bool value = false]) : super.dirty(value);

  @override
  AvailableFridayValidationError validator(bool value) {
    return null;
  }
}

enum AvailableSaturdayValidationError { invalid }
class AvailableSaturdayInput extends FormzInput<bool, AvailableSaturdayValidationError> {
  const AvailableSaturdayInput.pure() : super.pure(false);
  const AvailableSaturdayInput.dirty([bool value = false]) : super.dirty(value);

  @override
  AvailableSaturdayValidationError validator(bool value) {
    return null;
  }
}

enum AvailableSundayValidationError { invalid }
class AvailableSundayInput extends FormzInput<bool, AvailableSundayValidationError> {
  const AvailableSundayInput.pure() : super.pure(false);
  const AvailableSundayInput.dirty([bool value = false]) : super.dirty(value);

  @override
  AvailableSundayValidationError validator(bool value) {
    return null;
  }
}

enum PreferredShiftValidationError { invalid }
class PreferredShiftInput extends FormzInput<Shift, PreferredShiftValidationError> {
  const PreferredShiftInput.pure() : super.pure(Shift.MORNING_SHIFT);
  const PreferredShiftInput.dirty([Shift value]) : super.dirty(value);

  @override
  PreferredShiftValidationError validator(Shift value) {
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

