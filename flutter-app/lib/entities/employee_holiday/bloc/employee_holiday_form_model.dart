import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/employee_holiday/employee_holiday_model.dart';

enum DescriptionValidationError { invalid }
class DescriptionInput extends FormzInput<String, DescriptionValidationError> {
  const DescriptionInput.pure() : super.pure('');
  const DescriptionInput.dirty([String value = '']) : super.dirty(value);

  @override
  DescriptionValidationError validator(String value) {
    return null;
  }
}

enum StartDateValidationError { invalid }
class StartDateInput extends FormzInput<DateTime, StartDateValidationError> {
  StartDateInput.pure() : super.pure(DateTime.now());
  StartDateInput.dirty([DateTime value]) : super.dirty(value);

  @override
  StartDateValidationError validator(DateTime value) {
    return null;
  }
}

enum EndDateValidationError { invalid }
class EndDateInput extends FormzInput<DateTime, EndDateValidationError> {
  EndDateInput.pure() : super.pure(DateTime.now());
  EndDateInput.dirty([DateTime value]) : super.dirty(value);

  @override
  EndDateValidationError validator(DateTime value) {
    return null;
  }
}

enum EmployeeHolidayTypeValidationError { invalid }
class EmployeeHolidayTypeInput extends FormzInput<EmployeeHolidayType, EmployeeHolidayTypeValidationError> {
  const EmployeeHolidayTypeInput.pure() : super.pure(EmployeeHolidayType.ANNUAL_HOLIDAY);
  const EmployeeHolidayTypeInput.dirty([EmployeeHolidayType value]) : super.dirty(value);

  @override
  EmployeeHolidayTypeValidationError validator(EmployeeHolidayType value) {
    return null;
  }
}

enum ApprovedDateValidationError { invalid }
class ApprovedDateInput extends FormzInput<DateTime, ApprovedDateValidationError> {
  ApprovedDateInput.pure() : super.pure(DateTime.now());
  ApprovedDateInput.dirty([DateTime value]) : super.dirty(value);

  @override
  ApprovedDateValidationError validator(DateTime value) {
    return null;
  }
}

enum RequestedDateValidationError { invalid }
class RequestedDateInput extends FormzInput<DateTime, RequestedDateValidationError> {
  RequestedDateInput.pure() : super.pure(DateTime.now());
  RequestedDateInput.dirty([DateTime value]) : super.dirty(value);

  @override
  RequestedDateValidationError validator(DateTime value) {
    return null;
  }
}

enum HolidayStatusValidationError { invalid }
class HolidayStatusInput extends FormzInput<HolidayStatus, HolidayStatusValidationError> {
  const HolidayStatusInput.pure() : super.pure(HolidayStatus.REQUESTED);
  const HolidayStatusInput.dirty([HolidayStatus value]) : super.dirty(value);

  @override
  HolidayStatusValidationError validator(HolidayStatus value) {
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

enum RejectionReasonValidationError { invalid }
class RejectionReasonInput extends FormzInput<String, RejectionReasonValidationError> {
  const RejectionReasonInput.pure() : super.pure('');
  const RejectionReasonInput.dirty([String value = '']) : super.dirty(value);

  @override
  RejectionReasonValidationError validator(String value) {
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

