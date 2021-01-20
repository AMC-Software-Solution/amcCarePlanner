import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/timesheet/timesheet_model.dart';

enum DescriptionValidationError { invalid }
class DescriptionInput extends FormzInput<String, DescriptionValidationError> {
  const DescriptionInput.pure() : super.pure('');
  const DescriptionInput.dirty([String value = '']) : super.dirty(value);

  @override
  DescriptionValidationError validator(String value) {
    return null;
  }
}

enum TimesheetDateValidationError { invalid }
class TimesheetDateInput extends FormzInput<DateTime, TimesheetDateValidationError> {
  TimesheetDateInput.pure() : super.pure(DateTime.now());
  TimesheetDateInput.dirty([DateTime value]) : super.dirty(value);

  @override
  TimesheetDateValidationError validator(DateTime value) {
    return null;
  }
}

enum StartTimeValidationError { invalid }
class StartTimeInput extends FormzInput<String, StartTimeValidationError> {
  const StartTimeInput.pure() : super.pure('');
  const StartTimeInput.dirty([String value = '']) : super.dirty(value);

  @override
  StartTimeValidationError validator(String value) {
    return null;
  }
}

enum EndTimeValidationError { invalid }
class EndTimeInput extends FormzInput<String, EndTimeValidationError> {
  const EndTimeInput.pure() : super.pure('');
  const EndTimeInput.dirty([String value = '']) : super.dirty(value);

  @override
  EndTimeValidationError validator(String value) {
    return null;
  }
}

enum HoursWorkedValidationError { invalid }
class HoursWorkedInput extends FormzInput<int, HoursWorkedValidationError> {
  const HoursWorkedInput.pure() : super.pure(0);
  const HoursWorkedInput.dirty([int value = 0]) : super.dirty(value);

  @override
  HoursWorkedValidationError validator(int value) {
    return null;
  }
}

enum BreakStartTimeValidationError { invalid }
class BreakStartTimeInput extends FormzInput<String, BreakStartTimeValidationError> {
  const BreakStartTimeInput.pure() : super.pure('');
  const BreakStartTimeInput.dirty([String value = '']) : super.dirty(value);

  @override
  BreakStartTimeValidationError validator(String value) {
    return null;
  }
}

enum BreakEndTimeValidationError { invalid }
class BreakEndTimeInput extends FormzInput<String, BreakEndTimeValidationError> {
  const BreakEndTimeInput.pure() : super.pure('');
  const BreakEndTimeInput.dirty([String value = '']) : super.dirty(value);

  @override
  BreakEndTimeValidationError validator(String value) {
    return null;
  }
}

enum NoteValidationError { invalid }
class NoteInput extends FormzInput<String, NoteValidationError> {
  const NoteInput.pure() : super.pure('');
  const NoteInput.dirty([String value = '']) : super.dirty(value);

  @override
  NoteValidationError validator(String value) {
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

