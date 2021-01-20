import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/task/task_model.dart';

enum TaskNameValidationError { invalid }
class TaskNameInput extends FormzInput<String, TaskNameValidationError> {
  const TaskNameInput.pure() : super.pure('');
  const TaskNameInput.dirty([String value = '']) : super.dirty(value);

  @override
  TaskNameValidationError validator(String value) {
    return null;
  }
}

enum DescriptionValidationError { invalid }
class DescriptionInput extends FormzInput<String, DescriptionValidationError> {
  const DescriptionInput.pure() : super.pure('');
  const DescriptionInput.dirty([String value = '']) : super.dirty(value);

  @override
  DescriptionValidationError validator(String value) {
    return null;
  }
}

enum DateOfTaskValidationError { invalid }
class DateOfTaskInput extends FormzInput<DateTime, DateOfTaskValidationError> {
  DateOfTaskInput.pure() : super.pure(DateTime.now());
  DateOfTaskInput.dirty([DateTime value]) : super.dirty(value);

  @override
  DateOfTaskValidationError validator(DateTime value) {
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

enum StatusValidationError { invalid }
class StatusInput extends FormzInput<TaskStatus, StatusValidationError> {
  const StatusInput.pure() : super.pure(TaskStatus.ASSSIGNED);
  const StatusInput.dirty([TaskStatus value]) : super.dirty(value);

  @override
  StatusValidationError validator(TaskStatus value) {
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

